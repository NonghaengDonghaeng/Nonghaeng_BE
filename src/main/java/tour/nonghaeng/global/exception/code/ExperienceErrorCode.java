package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum ExperienceErrorCode implements BaseErrorCode{
    DEFAULT_EXPERIENCE_ERROR(HttpStatus.BAD_GATEWAY,"EXP_400_0","체험 기본 오류"),
    DUPLICATE_EXPERIENCE_OPEN_DATE_ADD_ERROR(HttpStatus.BAD_GATEWAY, "EXP_400_1", "중복된 날짜가 존재합니다."),
    ALREADY_EXIST_EXPERIENCE_OPEN_DATE_ADD_ERROR(HttpStatus.BAD_GATEWAY, "EXP_400_1", "이미 등록된 날짜입니다."),
    PAST_EXPERIENCE_OPEN_DATE_ADD_ERROR(HttpStatus.BAD_GATEWAY, "EXP_400_2", "현재날짜보다 과거 날짜를 등록할 수 없습니다."),
    OVERLAPS_ROUND_TIME_ADD_ERROR(HttpStatus.BAD_GATEWAY, "EXP_400_3", "이미 등록된 회차시간과 곂치는 시간입니다."),
    ;

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(this.status.value())
                .code(this.code)
                .reason(this.reason)
                .build();
    }
}
