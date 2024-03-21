package tour.nonghaeng.global.testEntity.experience;

import tour.nonghaeng.domain.etc.experience.ExperienceType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.time.LocalDate;

public class TestExperience {

    public static final ExperienceType EXPERIENCE_TYPE = ExperienceType.CRAFTING;
    public static final String EXPERIENCE_NAME = "소타기 체험";
    public static final LocalDate EXPERIENCE_START_DATE = LocalDate.of(2024, 1, 1);
    public static final LocalDate EXPERIENCE_END_DATE = LocalDate.of(2024, 12, 30);
    public static final int EXPERIENCE_MIN_PARTICIPANT = 5;
    public static final int EXPERIENCE_MAX_PARTICIPANT = 20;
    public static final int EXPERIENCE_PRICE = 5000;
    public static final int EXPERIENCE_DURATION_HOUR = 2;
    public static final String EXPERIENCE_CHECK_POINT = "---";
    public static final String EXPERIENCE_DETAIL_INTRODUCTION = "소타기 체험 디테일 설명 ~~";
    public static final String EXPERIENCE_SUMMARY = "재밌는 소타기 체험";
    public static final String EXPERIENCE_SUPPLIES = "준비물 없음";
    public static final String EXPERIENCE_PRECAUTIONS = "소타다 넘어져도 책임안져요.";

    public static int num = 0;

    public static Experience makeTestExperience(Tour tour) {
        return Experience.builder()
                .tour(tour)
                .experienceType(EXPERIENCE_TYPE)
                .experienceName(EXPERIENCE_NAME)
                .startDate(EXPERIENCE_START_DATE)
                .endDate(EXPERIENCE_END_DATE)
                .minParticipant(EXPERIENCE_MIN_PARTICIPANT)
                .maxParticipant(EXPERIENCE_MAX_PARTICIPANT)
                .price(EXPERIENCE_PRICE)
                .durationHours(EXPERIENCE_DURATION_HOUR)
                .checkPoint(EXPERIENCE_CHECK_POINT )
                .detailIntroduction(EXPERIENCE_DETAIL_INTRODUCTION)
                .summary(EXPERIENCE_SUMMARY )
                .supplies(EXPERIENCE_SUPPLIES)
                .precautions(EXPERIENCE_PRECAUTIONS)
                .build();

    }
}
