package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.experience.ExperienceType;
import tour.nonghaeng.domain.experience.dto.*;
import tour.nonghaeng.domain.experience.dto.specification.ExperienceSpecification;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceCloseDate;
import tour.nonghaeng.domain.experience.data.ExperienceRound;
import tour.nonghaeng.domain.experience.presentation.exception.ExperienceException;
import tour.nonghaeng.domain.experience.data.repo.ExperienceRepository;
import tour.nonghaeng.domain.experience.service.valid.ExperienceCloseDateValidator;
import tour.nonghaeng.domain.experience.service.valid.ExperienceValidator;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.service.ExperienceReservationService;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.infra.service.DefaultManagerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceService implements DefaultManagerService<Experience,ExpDetailDto> {

    private final ExperienceRepository experienceRepository;

    private final TourService tourService;
    private final ExperienceRoundService experienceRoundService;
    private final ExperienceCloseDateService experienceCloseDateService;
    private final ExperienceReservationService experienceReservationService;

    private final ExperienceValidator experienceValidator;
    private final ExperienceCloseDateValidator experienceCloseDateValidator;




    public Long createExperience(Member seller, CreateExpDto dto) {

        //TODO: dto 검증

        Experience experience = dto.toEntity(tourService.findBySeller(seller));

        experienceRepository.save(experience);

        experienceRoundService.addRounds(experience,dto.expRoundDtoList());

        return experienceRepository.save(experience).getId();
    }



    public Page<ExpSummaryDto> getExpSummaryDtoPage(Pageable pageable, String keyword, List<AreaCode> areaCodes, ExperienceType experienceType) {

        Specification<Experience> specification = ExperienceSpecification.buildSpecification(keyword, areaCodes, experienceType);

        Page<Experience> expPage = experienceRepository.findAll(specification, pageable);

        experienceValidator.pageValidate(expPage);

        Page<ExpSummaryDto> dto = ExpSummaryDto.toPageDto(expPage);

        return dto;
    }



    public Long addOnlyCloseDates(Long experienceId, List<AddExpCloseDateDto> dtoList) {

        Experience experience = findById(experienceId);

        experienceCloseDateService.addCloseDates(experience, dtoList);

        return experienceRepository.save(experience).getId();
    }



    public void removeOnlyCloseDates(Long experienceId, List<AddExpCloseDateDto> dtoList) {

        Experience experience = findById(experienceId);

        experienceCloseDateValidator.removeDtoListValidate(experience,dtoList);

        for (AddExpCloseDateDto addExpCloseDateDto : dtoList) {
            ExperienceCloseDate experienceCloseDate = experienceCloseDateService.findByExperienceAndCloseDate(experience, addExpCloseDateDto.closeDate());
            experience.removeCloseDate(experienceCloseDate);
        }
        experienceRepository.save(experience);
    }



    //TODO: experienceRepository 에 다시 저장하지 않아도 이미 반영된다.
    public Long addOnlyRounds(Long experienceId, List<AddExpRoundDto> dtoList) {

        Experience experience = findById(experienceId);

        experienceRoundService.addRounds(experience,dtoList);

        return experienceRepository.save(experience).getId();
    }



    public ExpRoundInfoDto getExpRoundInfoDto(Long experienceId, LocalDate dateParameter) {

        Experience experience = findById(experienceId);

        experienceCloseDateValidator.isOpenDateParameterValidate(experience,dateParameter);

        ExpRoundInfoDto dto = ExpRoundInfoDto.builder()
                .experienceId(experience.getId())
                .checkDate(dateParameter)
                .build();

        for (ExperienceRound round : experience.getExperienceRounds()) {
            ExpRoundInfoDto.RoundInfo roundInfo = ExpRoundInfoDto.RoundInfo.toRoundInfo(round);
            roundInfo.setRemainParticipant(experienceReservationService.countRemainOfParticipant(round, dateParameter));
            dto.addRoundInfo(roundInfo);
        }

        return dto;
    }



    public ExpDetailDto getExpDetailDto(Long experienceId) {

        return ExpDetailDto.toDto(findById(experienceId));
    }

    public List<Experience> findAll() {

        return experienceRepository.findAll();
    }



    public List<Long> findAllIds() {
        return experienceRepository.findAllIds();
    }

    public Experience findById(Long experienceId) {

        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> ExperienceException.EXCEPTION);
    }



    public void checkOldestCloseDatePastOrNot(Long experienceId) {

        Optional<LocalDate> oldestCloseDate = experienceRepository.findOldestCloseDate(experienceId);

        oldestCloseDate.ifPresent(localDate -> {
            if (localDate.isBefore(LocalDate.now())) {
                Experience experience = findById(experienceId);
                log.info("{} 날짜 오늘({})이 지나서 삭제", localDate, LocalDate.now());
                experience.removeCloseDate(
                        experienceCloseDateService
                                .findByExperienceAndCloseDate(experience, localDate));
                experienceRepository.save(experience);
            }
        });
    }


    @Override
    public ExpDetailDto getDetailDtoById(Long experienceId) {
        return ExpDetailDto.toDto(findById(experienceId));
    }

    @Override
    public Experience getEntityById(Long experienceId) {
        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> ExperienceException.EXCEPTION);
    }
}
