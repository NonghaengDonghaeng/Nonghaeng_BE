package tour.nonghaeng.domain.payment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import tour.nonghaeng.domain.reservation.data.Reservation;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestPaymentDto {
    private String reservationUid;
    private String itemName;
    private String buyerName;
    private int paymentPrice;
    private String buyerEmail;
    private String buyerNumber;

    @Builder
    public RequestPaymentDto(String reservationUid, String itemName, String buyerName, int paymentPrice, String buyerEmail, String buyerNumber) {
        this.reservationUid = reservationUid;
        this.itemName = itemName;
        this.buyerName = buyerName;
        this.paymentPrice = paymentPrice;
        this.buyerEmail = buyerEmail;
        this.buyerNumber = buyerNumber;
    }

    public static RequestPaymentDto toDto(Reservation reservation) {
        return RequestPaymentDto.builder()
                .reservationUid(reservation.getReservationUid())
                .itemName(reservation.getItemName())
                .buyerName(reservation.getReservationName())
                .paymentPrice(reservation.getPrice())
                .buyerEmail(reservation.getEmail())
                .buyerNumber(reservation.getNumber())
                .build();
    }
}
