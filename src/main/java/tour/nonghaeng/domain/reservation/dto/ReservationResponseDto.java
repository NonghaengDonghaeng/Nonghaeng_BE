package tour.nonghaeng.domain.reservation.dto;

import tour.nonghaeng.domain.reservation.dto.payment.RequestPaymentDto;

public class ReservationResponseDto {

    public RequestPaymentDto paymentDto;

    public ReservationResponseDto(RequestPaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }
}
