package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum S3ErrorCode implements BaseErrorCode{
    DEFAULT_S3_ERROR(HttpStatus.BAD_GATEWAY,"S3_400_0","S3 기본 오류"),
    CONVERT_TO_FILE_FROM_MULTI_PART_FILE(HttpStatus.BAD_GATEWAY,"S3_100_1","이미지 멀티파트파일에서 파일로 변환시 오류"),
    NOT_VALIDATE_IMAGE_URL(HttpStatus.BAD_GATEWAY, "S3_101_1", "타당하지 않은 imageUrl 입니다."),
    NOT_SUPPORT_EXTENSION(HttpStatus.BAD_GATEWAY, "S3_404_1", "지원하지 않는 확장자입니다."),
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
