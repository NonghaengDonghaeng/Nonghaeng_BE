package tour.nonghaeng.domain.payment.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentCallbackRequestDto {
    private String paymentUid; // 결제 고유 번호
    private String reservationUid; // 주문 고유 번호
}
