package tour.nonghaeng.domain.etc.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReviewServiceType {

    EXPERIENCE("experience"),
    ROOM("room"),
    TOUR_AND_ALL("etc"),
    ;

    private final String dtype;

    public static ReviewServiceType ofDtype(String dtype) {
        if (dtype == null) {
            throw new IllegalArgumentException();
        }
        for (ReviewServiceType rt : ReviewServiceType.values()) {
            if (rt.getDtype().equals(dtype)) {
                return rt;
            }
        }
        throw new IllegalArgumentException("일치하는 사진타입이 없습니다.");
    }
}
