package tour.nonghaeng.domain.etc.like;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LikeType {

    TOUR("tour"),
    EXPERIENCE("experience"),
    ROOM("room"),
    REVIEW("review"),
    ;

    private final String dtype;

    public static LikeType ofDtype(String dtype) {
        if (dtype == null) {
            throw new IllegalArgumentException();
        }
        for (LikeType lt : LikeType.values()) {
            if (lt.getDtype().equals(dtype)) {
                return lt;
            }
        }
        throw new IllegalArgumentException("일치하는 사진타입이 없습니다.");
    }
}
