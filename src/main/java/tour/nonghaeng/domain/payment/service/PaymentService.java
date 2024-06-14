package tour.nonghaeng.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tour.nonghaeng.domain.payment.data.Payment;
import tour.nonghaeng.domain.payment.data.repo.PaymentRepository;
import tour.nonghaeng.domain.payment.dto.CancelRequestDto;
import tour.nonghaeng.domain.payment.dto.IamportResponseDto;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.data.repo.ReservationRepository;
import tour.nonghaeng.global.infra.enums.payment.PaymentStatus;

@Service
@RequiredArgsConstructor
@Transactional
@Setter
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final WebClient webClient;
    private final ReservationRepository reservationRepository;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }


    public IamportResponseDto paymentValid(String paymentId) {
        IamportResponseDto responseDto = getPayment(paymentId);

        Reservation reservation = reservationRepository.findReservationAndPayment(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("주문 내역이 없습니다."));

        if(!responseDto.getStatus().equals("PAID")){
            reservationRepository.delete(reservation);
            paymentRepository.delete(reservation.getPayment());

            throw new RuntimeException("결제 미완료");
        }

        // DB에 저장된 결제 금액
        int price = reservation.getPayment().getPrice();
        int iamportPrice = responseDto.getAmount().getTotal();

        // 결제 금액 검증
        if(iamportPrice != price) {
            // 주문, 결제 삭제
            reservationRepository.delete(reservation);
            paymentRepository.delete(reservation.getPayment());

            // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
            cancelPaymentByImpUid(paymentId);

            throw new RuntimeException("결제금액 위변조 의심");
        }

        reservation.getPayment().changePaymentBySuccess(PaymentStatus.OK, responseDto.getId());

        return responseDto;

    }

    private IamportResponseDto getPayment(String paymentId) {

        return webClient.get()
                .uri("/payments/"+paymentId)
                .retrieve()
                .bodyToMono(IamportResponseDto.class)
                .block();

    }


    private void cancelPaymentByImpUid(String paymentId) {

        CancelRequestDto body = CancelRequestDto.builder().reason("금액 위변조로 인한 취소").build();

        webClient.post()
                .uri("/payments/"+paymentId+"/cancel")
                .body(Mono.just(body),CancelRequestDto.class)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(e -> new RuntimeException("Payment cancellation failed", e));

    }

}
