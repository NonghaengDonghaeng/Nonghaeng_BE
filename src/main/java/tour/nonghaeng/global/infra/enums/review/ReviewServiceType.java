package tour.nonghaeng.global.infra.enums.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tour.nonghaeng.global.infra.exception.GlobalException;
import tour.nonghaeng.global.infra.exception.error.GlobalErrorCode;

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
            throw new GlobalException(GlobalErrorCode.CODE_IS_NULL_ERROR);
        }
        for (ReviewServiceType rt : ReviewServiceType.values()) {
            if (rt.getDtype().equals(dtype)) {
                return rt;
            }
        }
        throw new GlobalException(GlobalErrorCode.NO_MATCH_REVIEW_TYPE);
    }
}
