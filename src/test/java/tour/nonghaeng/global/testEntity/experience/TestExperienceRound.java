package tour.nonghaeng.global.testEntity.experience;

import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;

import java.time.LocalTime;

public class TestExperienceRound {

    public static final int EXPERIENCE_ROUND_START_TIME = 10;


    public static ExperienceRound makeTestExperienceRound(Experience experience) {

        return ExperienceRound.builder()
                .experience(experience)
                .startTime(LocalTime.of(EXPERIENCE_ROUND_START_TIME,0))
                .endTime(LocalTime.of(EXPERIENCE_ROUND_START_TIME+2, 0))
                .maxParticipant(experience.getMaxParticipant())
                .build();
    }

    public static ExperienceRound makeTestExperienceRound(Experience experience,int startTime) {

        return ExperienceRound.builder()
                .experience(experience)
                .startTime(LocalTime.of(startTime, 0))
                .endTime(LocalTime.of(startTime+2, 0))
                .maxParticipant(experience.getMaxParticipant())
                .build();
    }

}
