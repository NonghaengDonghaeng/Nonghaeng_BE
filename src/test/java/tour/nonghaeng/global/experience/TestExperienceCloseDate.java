package tour.nonghaeng.global.experience;

import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceCloseDate;

import java.time.LocalDate;

public class TestExperienceCloseDate {

    public static ExperienceCloseDate makeTestExperienceCloseDate(Experience experience,LocalDate closeDate) {
        return ExperienceCloseDate.builder()
                .experience(experience)
                .closeDate(closeDate)
                .build();
    }

}
