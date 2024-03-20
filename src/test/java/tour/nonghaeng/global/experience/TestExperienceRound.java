package tour.nonghaeng.global.experience;

import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;

import java.time.LocalTime;

public class TestExperienceRound {

    public static final int EXPERIENCE_ROUND_START_TIME = 10;
    public static final int EXPERIENCE_ROUND_END_TIME = 11;

    public static int num = 0;

    public static ExperienceRound makeTestExperienceRound(Experience experience) {
        return ExperienceRound.builder()
                .experience(experience)
                .startTime(LocalTime.of(EXPERIENCE_ROUND_START_TIME + (num++), 0))
                .endTime(LocalTime.of(EXPERIENCE_ROUND_END_TIME + (num++), 0))
                .maxParticipant(experience.getMaxParticipant())
                .build();
    }

}
