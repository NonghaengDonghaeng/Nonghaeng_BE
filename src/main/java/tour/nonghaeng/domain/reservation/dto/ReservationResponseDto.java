package tour.nonghaeng.domain.reservation.dto;

import tour.nonghaeng.domain.payment.dto.RequestPaymentDto;

public class ReservationResponseDto {

    public RequestPaymentDto paymentDto;

    public ReservationResponseDto(RequestPaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }
}
