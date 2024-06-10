package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceCloseDate;
import tour.nonghaeng.domain.experience.data.ExperienceRound;
import tour.nonghaeng.domain.experience.data.repo.ExperienceRepository;
import tour.nonghaeng.domain.experience.dto.*;
import tour.nonghaeng.domain.experience.presentation.exception.ExperienceException;
import tour.nonghaeng.domain.experience.service.valid.ExperienceCloseDateValidator;
import tour.nonghaeng.domain.experience.service.valid.ExperienceValidator;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.service.ExperienceReservationService;
import tour.nonghaeng.domain.tour.service.TourService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    private final TourService tourService;
    private final ExperienceRoundService experienceRoundService;
    private final ExperienceCloseDateService experienceCloseDateService;
    private final ExperienceReservationService experienceReservationService;

    private final ExperienceValidator experienceValidator;
    private final ExperienceCloseDateValidator experienceCloseDateValidator;




    @Override
    public Experience findById(Long experienceId) {

        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> ExperienceException.EXCEPTION);
    }


    @Override
    public Experience create(Member seller, CreateExpDto dto) {

        //TODO: dto 검증

        Experience experience = dto.toEntity(tourService.findBySeller(seller));

        experienceRepository.save(experience);

        experienceRoundService.addRounds(experience,dto.getExpRoundDtoList());

        return experienceRepository.save(experience);
    }



    @Override
    public Page<ExpSummaryDto> getSummaryDtoPage(Pageable pageable, ExpSpecDto expSpecDto) {

        Page<Experience> expPage = experienceRepository.findAll(expSpecDto.buildSpecification(), pageable);

        experienceValidator.pageValidate(expPage);

        return ExpSummaryDto.toPageDto(expPage);
    }


    @Override
    public ExpDetailDto getDetailDto(Long experienceId) {

        return ExpDetailDto.toDto(findById(experienceId));
    }



    //TODO: experienceRepository 에 다시 저장하지 않아도 이미 반영된다.
    @Override
    public void addOnlyRounds(Long experienceId, List<AddExpRoundDto> dtoList) {

        Experience experience = findById(experienceId);

        experienceRoundService.addRounds(experience,dtoList);

        experienceRepository.save(experience);
    }


    @Override
    public ExpRoundInfoDto getExpRoundInfoDto(Long experienceId, LocalDate dateParameter) {

        Experience experience = findById(experienceId);

        experienceCloseDateValidator.isOpenDateParameterValidate(experience,dateParameter);

        ExpRoundInfoDto dto = ExpRoundInfoDto.builder()
                .experienceId(experience.getId())
                .checkDate(dateParameter)
                .build();

        for (ExperienceRound round : experience.getExperienceRounds()) {
            ExpRoundInfoDto.RoundInfo roundInfo = ExpRoundInfoDto.RoundInfo.toRoundInfo(round);
            roundInfo.setRemainParticipant(experienceReservationService.countRemain(round, dateParameter));
            dto.addRoundInfo(roundInfo);
        }

        return dto;
    }



    @Override
    public void addOnlyCloseDates(Long experienceId, List<AddExpCloseDateDto> dtoList) {

        Experience experience = findById(experienceId);

        experienceCloseDateService.addCloseDates(experience, dtoList);

        experienceRepository.save(experience);
    }


    @Override
    public void removeOnlyCloseDates(Long experienceId, List<AddExpCloseDateDto> dtoList) {

        Experience experience = findById(experienceId);

        experienceCloseDateValidator.removeDtoListValidate(experience,dtoList);

        for (AddExpCloseDateDto addExpCloseDateDto : dtoList) {
            ExperienceCloseDate experienceCloseDate = experienceCloseDateService.findByExperienceAndCloseDate(experience, addExpCloseDateDto.getCloseDate());
            experience.removeCloseDate(experienceCloseDate);
        }
        experienceRepository.save(experience);
    }


    @Override
    public List<Long> findAllIds() {
        return experienceRepository.findAllIds();
    }


    @Override
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


}
