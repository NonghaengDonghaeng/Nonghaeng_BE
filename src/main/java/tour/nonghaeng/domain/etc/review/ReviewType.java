package tour.nonghaeng.domain.etc.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tour.nonghaeng.domain.etc.photo.PhotoType;

@AllArgsConstructor
@Getter
public enum ReviewType {

    EXPERIENCE("experience"),
    ROOM("room"),
    ;

    private final String dtype;

    public static ReviewType ofDtype(String dtype) {
        if (dtype == null) {
            throw new IllegalArgumentException();
        }
        for (ReviewType rt : ReviewType.values()) {
            if (rt.getDtype().equals(dtype)) {
                return rt;
            }
        }
        throw new IllegalArgumentException("일치하는 사진타입이 없습니다.");
    }
}
