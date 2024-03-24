package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum S3ErrorCode implements BaseErrorCode{
    DEFAULT_S3_ERROR(HttpStatus.BAD_GATEWAY,"S3_400_0","S3 기본 오류"),
    CONVERT_TO_FILE_FROM_MULTI_PART_FILE(HttpStatus.BAD_GATEWAY,"S3_100_1","이미지 멀티파트파일에서 파일로 변환시 오류")
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
