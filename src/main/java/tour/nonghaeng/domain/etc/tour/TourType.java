package tour.nonghaeng.domain.etc.tour;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TourType {
    VILLAGE("1","농촌체험휴양마을"),
    EDUCATION_FARM("2","농촌교육농장"),
    TOURIST_FARM("3","우수관광농원"),
    RANCH("4","낙농체험목장"),
    BED_BREAKFAST("5","농촌민박"),
    HORSE_RIDING("6","농어촌승마장"),
    NATURAL_FOREST("7","자연휴양림"),
    HEALING_FOREST("8","치유의 숲"),
    CAMPING("(","농촌캠핑장"),
    ETC("10","그 외")
    ;

    private final String code;
    private final String name;

    public static TourType ofCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException();
        }
        for (TourType tt : TourType.values()) {
            if (tt.getCode().equals(code)) {
                return tt;
            }
        }
        throw new IllegalArgumentException("일치하는 관광타입코드가 없습니다.");
    }
}
