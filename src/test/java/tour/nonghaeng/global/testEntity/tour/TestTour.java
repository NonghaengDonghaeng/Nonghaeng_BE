package tour.nonghaeng.global.testEntity.tour;

import tour.nonghaeng.domain.etc.tour.TourType;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.entity.Tour;

public class TestTour {

    public static final String TOUR_NAME = "낙양체험마을";
    public static final String TOUR_HOMEPAGE_URL = "낙양체험마을.com";
    public static final String TOUR_INTRODUCTION = "낙양체험마을은 농촌체험을 많이 즐길 수 있는 마을입니다.";
    public static final String TOUR_ONE_LINE_INTRO = "낙양체험마을은 체험마을의 꽃";
    public static final String TOUR_SUMMARY = "낙양체험마을 요약";
    public static final String TOUR_RESTAURANT = "마을 식당 1개 보유";
    public static final String TOUR_PARKING = "주차장 보유, 50대 수용";
    public static final String TOUR_TOILET = "화장실 보유, 마을회관에 위치";
    public static final String TOUR_AMENITIES = "부대시설: 편의점 보유";

    public static int num = 0;


    public static Tour makeTestTour(Seller seller) {
        return Tour.builder()
                .seller(seller)
                .tourType(TourType.ETC)
                .name(TOUR_NAME + String.valueOf(num++))
                .homepageUrl(TOUR_HOMEPAGE_URL + String.valueOf(num++))
                .introduction(TOUR_INTRODUCTION + String.valueOf(num++))
                .oneLineIntro(TOUR_ONE_LINE_INTRO + String.valueOf(num++))
                .summary(TOUR_SUMMARY + String.valueOf(num++))
                .restaurant(TOUR_RESTAURANT + String.valueOf(num++))
                .parking(TOUR_PARKING + String.valueOf(num++))
                .toilet(TOUR_TOILET + String.valueOf(num++))
                .amenities(TOUR_AMENITIES + String.valueOf(num++))
                .build();
    }

}
