package tour.nonghaeng.global.testEntity.experience;

import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceCloseDate;

import java.time.LocalDate;

public class TestExperienceCloseDate {

    public static ExperienceCloseDate makeTestExperienceCloseDate(Experience experience,LocalDate closeDate) {
        return ExperienceCloseDate.builder()
                .experience(experience)
                .closeDate(closeDate)
                .build();
    }

}
