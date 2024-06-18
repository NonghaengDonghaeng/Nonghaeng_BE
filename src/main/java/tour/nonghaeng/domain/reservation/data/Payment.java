package tour.nonghaeng.domain.reservation.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.enums.payment.PaymentStatus;

@Entity
@Table(name = "PAYMENTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;
    private int price;
    private PaymentStatus status;
    private String paymentUid; // 결제 고유 번호

    @Builder
    public Payment(int price, PaymentStatus status, String paymentUid) {
        this.price = price;
        this.status = status;
        this.paymentUid = paymentUid;
    }

    public void changePaymentBySuccess(PaymentStatus status, String paymentId) {
        this.status = status;
        this.paymentUid = paymentId;
    }

}
