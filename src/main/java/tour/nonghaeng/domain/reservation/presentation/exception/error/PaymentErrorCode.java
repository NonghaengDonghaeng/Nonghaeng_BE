package tour.nonghaeng.domain.reservation.presentation.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.infra.exception.dto.ErrorReason;
import tour.nonghaeng.global.infra.exception.error.BaseErrorCode;

@AllArgsConstructor
@Getter
public enum PaymentErrorCode implements BaseErrorCode {

    DEFAULT_PAYMENT_ERROR(HttpStatus.BAD_GATEWAY,"PAY_400_0","결제 기본 오류"),
    NOT_PAID_ERROR(HttpStatus.BAD_GATEWAY,"PAY_400_1","결제 미완료"),
    FORGERY_PAYMENT_ERROR(HttpStatus.BAD_GATEWAY,"PAY_400_2","결제 위변조 의심 오류"),
    NO_PAYMENT_HISTORY_ERROR(HttpStatus.BAD_GATEWAY,"PAY_400_1","주문내역이 없습니다."),
    PAYMENT_CANCEL_FAILED_ERROR(HttpStatus.BAD_GATEWAY,"PAY_400_1","결제 취소 에러"),

    ;
    private final HttpStatus status;
    private final String code;
    private final String reason;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(status.value())
                .code(this.code)
                .reason(this.reason)
                .build();
    }
}
