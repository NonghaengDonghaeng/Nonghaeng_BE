package tour.nonghaeng.domain.experience.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.*;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceOpenDate;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.experience.repo.ExperienceRepository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.validation.ExperienceOpenDateValidator;
import tour.nonghaeng.global.validation.ExperienceValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    private final ExperienceValidator experienceValidator;
    private final ExperienceOpenDateValidator experienceOpenDateValidator;

    private final ExperienceRoundService experienceRoundService;
    private final ExperienceOpenDateService experienceOpenDateService;

    private final TourService tourService;

    public Page<ExpSummaryDto> findAll(Pageable pageable) {

        Page<Experience> expPage = experienceRepository.findAll(pageable);

        experienceValidator.pageValidate(expPage);

        Page<ExpSummaryDto> dto = ExpSummaryDto.convert(expPage);

        return dto;
    }

    public Long add(Seller seller, CreateExpDto dto) {
        //TODO: dto 검증

        //체험 엔티티 생성
        Tour tour = tourService.findBySeller(seller);
        Experience experience = dto.toEntity(seller, tour);

        //체험회차 엔티티 생성,저장 후 체험엔티티에 연결
        experienceRoundService.addRounds(experience,dto.expRoundDtoList());

        return experienceRepository.save(experience).getId();

    }

    public Long addOnlyOpenDates(Long experienceId, List<AddExpOpenDateDto> dtoList) {

        Experience experience = findById(experienceId);

        experienceOpenDateService.addOpenDates(experience, dtoList);

        return experienceRepository.save(experience).getId();

    }

    public void closeOnlyOpenDates(Long experienceId, List<AddExpOpenDateDto> dtoList) {

        Experience experience = findById(experienceId);

        for (AddExpOpenDateDto addExpOpenDateDto : dtoList) {
            ExperienceOpenDate experienceOpenDate = experienceOpenDateService.findByExperienceAndOpenDates(experience, addExpOpenDateDto.openDate());
            experience.deleteOpenDate(experienceOpenDate);
        }
        experienceRepository.save(experience);

    }

    public Long addOnlyRounds(Long experienceId, List<AddExpRoundDto> dtoList) {

        Experience experience = findById(experienceId);

        experienceRoundService.addRounds(experience,dtoList);

        return experienceRepository.save(experience).getId();
    }

    //TODO: 이미 컨트롤러에서 체험이 존재하는지와 요청 판매자의 소유인지를 확인하는 검증이 들어가기 때문에 여기서 또 할 필요가 없어서 뺄지 말지 고민
    public Experience findById(Long experienceId) {
        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> ExperienceException.EXCEPTION);
    }

    public Experience findBySeller(Seller seller) {
        return experienceRepository.findBySeller(seller)
                .orElseThrow(() -> ExperienceException.EXCEPTION);
    }

    public ExpRoundInfoDto getExpRoundInfo(Long experienceId, LocalDate dateParameter) {

        Experience experience = findById(experienceId);

        experienceOpenDateValidator.openDateParameterValidate(experience,dateParameter);

        ExpRoundInfoDto dto = ExpRoundInfoDto.builder()
                .experienceId(experience.getId())
                .checkDate(dateParameter)
                .build();

        for (ExperienceRound round : experience.getExperienceRounds()) {
            ExpRoundInfoDto.RoundInfo roundInfo = ExpRoundInfoDto.RoundInfo.convert(round);
            //TODO: 현재는 예약을 구현안해서 남은인원이 아니라 최대인원으로 설정함. 이후에 예약 구현시 남은 시간으로 로직 변경
            roundInfo.setRemainParticipant(experience.getMaxParticipant());
            dto.addRoundInfo(roundInfo);
        }

        return dto;
    }

    public ExpDetailDto findByExpId(Long experienceId) {

        experienceValidator.expIdValidate(experienceId);

        return ExpDetailDto.convert(experienceRepository.findById(experienceId).get());

    }

    //TODO: 스케줄 매일마다 가장 오래된 날짜 오늘과 확인후 삭제작업, 시간대 및 성능적 코드개선 필요
    @Async
    @Scheduled(cron = "0 0 0 * * *")
    public void autoOpenDatesDeleted() {
        log.info("Scheduler 실행: autoOpenDatesDeleted");
        List<Experience> experiences = experienceRepository.findAll();
        for (Experience exp : experiences) {
            checkOldestOpenDatePastOrNot(exp);
        }
    }

    private void checkOldestOpenDatePastOrNot(Experience experience) {
        Optional<LocalDate> oldestOpenDate = experienceRepository.findOldestOpenDate(experience);

        oldestOpenDate.ifPresent(localDate -> {
            if (localDate.isBefore(LocalDate.now())) {
                log.info("{} 날짜 오늘({})이 지나서 삭제", localDate.toString(), LocalDate.now().toString());
                experience.deleteOpenDate(
                        experienceOpenDateService
                                .findByExperienceAndOpenDates(experience, localDate));
                experienceRepository.save(experience);
            }
        });
    }


}
