package tour.nonghaeng.domain.reservation.dto.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import tour.nonghaeng.domain.reservation.data.Reservation;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestPaymentDto {
    private String paymentUid;
    private String itemName;
    private int paymentPrice;
    private String buyerName;
    private String buyerEmail;
    private String buyerNumber;

    @Builder
    public RequestPaymentDto(String paymentUid, String itemName, int paymentPrice, String buyerName, String buyerEmail, String buyerNumber) {
        this.paymentUid = paymentUid;
        this.itemName = itemName;
        this.paymentPrice = paymentPrice;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerNumber = buyerNumber;
    }

    public static RequestPaymentDto toDto(Reservation reservation) {
        return RequestPaymentDto.builder()
                .paymentUid(reservation.getPayment().getPaymentUid())
                .itemName(reservation.getItemName())
                .buyerName(reservation.getReservationName())
                .paymentPrice(reservation.getPrice())
                .buyerEmail(reservation.getEmail())
                .buyerNumber(reservation.getNumber())
                .build();
    }
}
