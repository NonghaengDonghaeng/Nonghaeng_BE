package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum ExperienceErrorCode implements BaseErrorCode{
    DEFAULT_EXPERIENCE_ERROR(HttpStatus.BAD_GATEWAY,"EXP_400_0","체험 기본 오류"),
    //해당 페이지에 체험이 없을시 에러코드
    NO_EXPERIENCE_CONTENT_AT_CURRENT_PAGE_ERROR(HttpStatus.NOT_FOUND, "EXP_105_1", "현재 페이지에 체험이 없습니다."),
    //오픈날짜 추가 등록시 에러코드
    DUPLICATE_EXPERIENCE_OPEN_DATE_ADD_ERROR(HttpStatus.BAD_GATEWAY, "EXP_100_1", "중복된 날짜가 존재합니다."),
    ALREADY_EXIST_EXPERIENCE_OPEN_DATE_ADD_ERROR(HttpStatus.BAD_GATEWAY, "EXP_100_2", "이미 등록된 날짜입니다."),
    PAST_EXPERIENCE_OPEN_DATE_ADD_ERROR(HttpStatus.BAD_GATEWAY, "EXP_100_3", "현재날짜보다 과거 날짜를 등록할 수 없습니다."),
    //오픈날짜 추가 삭제시 에러코드
    NOT_EXIST_EXPERIENCE_OPEN_DATE_ERROR(HttpStatus.BAD_GATEWAY, "EXP_101_1", "해당 체험에 등록되지 않은 날짜입니다."),
    //회차 추가 등록시 에러코드
    OVERLAPS_ROUND_TIME_ADD_ERROR(HttpStatus.BAD_GATEWAY, "EXP_102_1", "이미 등록된 회차시간과 곂치는 시간입니다."),

    NO_EXIST_EXPERIENCE_BY_EXPERIENCE_ID_ERROR(HttpStatus.BAD_GATEWAY, "EXP_000_1", "experienceID에 해당하는 체험이 없습니다."),
    NO_OWNER_AUTHORIZATION_ERROR(HttpStatus.BAD_GATEWAY, "EXP_000_2", "해당 체험에 대한 소유자가 아닙니다."),
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
