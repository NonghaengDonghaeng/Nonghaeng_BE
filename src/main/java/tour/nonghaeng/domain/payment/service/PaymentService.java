package tour.nonghaeng.domain.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.payment.data.Payment;
import tour.nonghaeng.domain.payment.data.repo.PaymentRepository;
import tour.nonghaeng.domain.payment.dto.PaymentCallbackRequestDto;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.data.repo.ReservationRepository;
import tour.nonghaeng.global.infra.enums.payment.PaymentStatus;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
@Setter
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;
    private final ReservationRepository reservationRepository;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

//    public RequestPaymentDto findRequestDto(String reservationUid) {
//
//        Reservation reservation = reservationRepository.findReservationAndPaymentAndMember(reservationUid)
//                .orElseThrow(() -> new IllegalArgumentException("예약이 없습니다."));
//
//        return RequestPaymentDto.builder()
//                .buyerName(reservation.getReservationName())
//                .buyerEmail(reservation.getEmail())
//                .buyerNumber(reservation.getNumber())
//                .paymentPrice(reservation.getPayment().getPrice())
//                .itemName(reservation.getItemName())
//                .reservationUid(reservation.getReservationUid())
//                .build();
//
//    }


    public IamportResponse<com.siot.IamportRestClient.response.Payment> paymentByCallback(PaymentCallbackRequestDto request) {

        try {
            // 결제 단건 조회(아임포트)
            IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());

            // 주문내역 조회
            Reservation reservation = reservationRepository.findReservationAndPayment(request.getReservationUid())
                    .orElseThrow(() -> new IllegalArgumentException("주문 내역이 없습니다."));

            // 결제 완료가 아니면
            if(!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 주문, 결제 삭제
                reservationRepository.delete(reservation);
                paymentRepository.delete(reservation.getPayment());

                throw new RuntimeException("결제 미완료");
            }

            // DB에 저장된 결제 금액
            int price = reservation.getPayment().getPrice();
            // 실 결제 금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if(iamportPrice != price) {
                // 주문, 결제 삭제
                reservationRepository.delete(reservation);
                paymentRepository.delete(reservation.getPayment());

                // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

                throw new RuntimeException("결제금액 위변조 의심");
            }

            // 결제 상태 변경
            reservation.getPayment().changePaymentBySuccess(PaymentStatus.OK, iamportResponse.getResponse().getImpUid());

            return iamportResponse;

        } catch (IamportResponseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
