package tour.nonghaeng.global.infra.enums.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tour.nonghaeng.global.infra.exception.GlobalException;
import tour.nonghaeng.global.infra.exception.error.GlobalErrorCode;

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
            throw new GlobalException(GlobalErrorCode.CODE_IS_NULL_ERROR);
        }
        for (LikeType lt : LikeType.values()) {
            if (lt.getDtype().equals(dtype)) {
                return lt;
            }
        }
        throw new GlobalException(GlobalErrorCode.NO_MATCH_LIKE_TYPE);
    }
}
