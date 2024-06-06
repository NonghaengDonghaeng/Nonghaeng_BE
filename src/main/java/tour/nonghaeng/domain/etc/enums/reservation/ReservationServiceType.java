package tour.nonghaeng.domain.etc.enums.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
            throw new IllegalArgumentException();
        }
        for (ReservationServiceType rt : ReservationServiceType.values()) {
            if (rt.getDtype().equals(dtype)) {
                return rt;
            }
        }
        throw new IllegalArgumentException("일치하는 사진타입이 없습니다.");
    }
}
