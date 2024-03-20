package tour.nonghaeng.global.experience;

import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceCloseDate;

import java.time.LocalDate;

public class TestExperienceCloseDate {

    public static final int EXPERIENCE_CLOSE_DATE_YEAR = 2024;
    public static final int EXPERIENCE_CLOSE_DATE_MONTH = 5;
    public static final int EXPERIENCE_CLOSE_DATE_DAY = 5;

    public static int num = 0;

    public static ExperienceCloseDate makeTestExperienceCloseDate(Experience experience) {
        return ExperienceCloseDate.builder()
                .experience(experience)
                .closeDate(LocalDate.of(EXPERIENCE_CLOSE_DATE_YEAR, EXPERIENCE_CLOSE_DATE_MONTH, EXPERIENCE_CLOSE_DATE_DAY + (num++)))
                .build();
    }

}
