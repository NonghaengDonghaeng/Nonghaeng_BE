package tour.nonghaeng.global.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.room.service.RoomService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SchedulerService {

    private static final int FIXED_DELAY = 10 * 60 * 1000;  //10분
    private static final String SCHEDULE_CRON_FOR_ONE_MINUTES = "0 0/1 * * * *";
    private static final String SCHEDULE_CRON_FOR_ONE_HOURS = "0 0 0/1 * * *";
    private static final String SCHEDULE_CRON_FOR_ONE_DAYS = "0 0 0 * * *";


    private final ExperienceService experienceService;
    private final RoomService roomService;


//    @Scheduled(fixedDelay = FIXED_DELAY)
//    public void runTest() {
//
//        log.info("10초마다 실행 스케줄러 test");
//    }

    //1시간 단위로 스케줄링
    //TODO: 스케줄 매일마다 가장 오래된 날짜 오늘과 확인후 삭제작업, 시간대 및 성능적 코드개선 필요
    @Async
    @Scheduled(cron = SCHEDULE_CRON_FOR_ONE_MINUTES)
    public void autoExpCloseDateDelete() {

        log.info("Scheduler 실행: autoExpCloseDatesDeleted");

        experienceService.findAllIds()
                .forEach(experienceId -> experienceService.checkOldestCloseDatePastOrNot(experienceId));
    }

    //테스트를 위한 1분 단위 실행
    @Async
    @Scheduled(cron = SCHEDULE_CRON_FOR_ONE_MINUTES)
    public void autoRoomCloseDateDelete() {

        log.info("Scheduler 실행: autoRoomCloseDatesDeleted");

        roomService.findAllIds()
                .forEach(roomId -> roomService.checkOldestCloseDatePastOrNot(roomId));
    }
}
