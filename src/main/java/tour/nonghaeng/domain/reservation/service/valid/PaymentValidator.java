package tour.nonghaeng.domain.reservation.service.valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.data.repo.PaymentRepository;
import tour.nonghaeng.domain.reservation.data.repo.ReservationRepository;
import tour.nonghaeng.domain.reservation.dto.payment.PortOneResponseDto;
import tour.nonghaeng.domain.reservation.presentation.exception.PaymentException;
import tour.nonghaeng.domain.reservation.presentation.exception.error.PaymentErrorCode;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentValidator {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public void paidValidate(PortOneResponseDto responseDto, Reservation reservation) {

        if(!responseDto.getStatus().equals("PAID")){
            reservationRepository.delete(reservation);
            paymentRepository.delete(reservation.getPayment());

            throw new PaymentException(PaymentErrorCode.NOT_PAID_ERROR);
        }
    }

    public boolean priceValidate(PortOneResponseDto responseDto, Reservation reservation) {

        int price = reservation.getPayment().getPrice();
        int iamportPrice = responseDto.getAmount().getTotal();

        // 결제 금액 검증
        if(iamportPrice != price) {
            // 주문, 결제 삭제
            reservationRepository.delete(reservation);
            paymentRepository.delete(reservation.getPayment());

            return false;
        }
        return true;
    }
}
