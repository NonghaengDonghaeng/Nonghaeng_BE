package tour.nonghaeng.domain.etc.enums.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;

@AllArgsConstructor
@Getter
public enum MemberServiceType {
    USER("user"),
    SELLER("seller"),
    TOUR_AND_ALL("etc"),
    ;

    private final String dtype;

    public static MemberServiceType ofDtype(String dtype) {
        if (dtype == null) {
            throw new IllegalArgumentException();
        }
        for (MemberServiceType rt : MemberServiceType.values()) {
            if (rt.getDtype().equals(dtype)) {
                return rt;
            }
        }
        return null;
    }

}
