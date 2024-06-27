package tour.nonghaeng.global.infra.enums.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tour.nonghaeng.global.infra.exception.GlobalException;
import tour.nonghaeng.global.infra.exception.error.GlobalErrorCode;

@AllArgsConstructor
@Getter
public enum ReservationServiceType {

    EXPERIENCE("experience"),
    ROOM("room"),
    TOUR_AND_ALL("all"),
    ;

    private final String dtype;

    public static ReservationServiceType ofDtype(String dtype) {
        if (dtype == null) {
            throw new GlobalException(GlobalErrorCode.CODE_IS_NULL_ERROR);
        }
        for (ReservationServiceType rt : ReservationServiceType.values()) {
            if (rt.getDtype().equals(dtype)) {
                return rt;
            }
        }
        throw new GlobalException(GlobalErrorCode.NO_MATCH_RESERVATION_TYPE);
    }
}
