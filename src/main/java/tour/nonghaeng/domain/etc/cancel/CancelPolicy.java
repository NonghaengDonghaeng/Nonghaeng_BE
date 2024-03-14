package tour.nonghaeng.domain.etc.cancel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CancelPolicy {
    NOT_CONFIRM_CANCEL_POLICY("예약 대기중 취소 수수료 정책",0),
    MISTAKE_CANCEL_POLICY("당일 취소 수수료 정책", 0),
    DEFAULT_CANCEL_POLICY("기본 수수료 정책", 0.07),
    IN_ONE_WEEK_CANCEL_POLICY("일주일 전 취소 수수료 정책",0.1),
    IN_ONE_DAY_CANCEL_POLICY("하루 전 취소 수수료 정책", 0.2),
    IN_SEVEN_HOURS_CANCEL_POLICY("7시간 안 취소 수수료 정책", 0.5),
    ;
    private final String name;
    private final double percent;
}
