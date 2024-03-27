package tour.nonghaeng.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import tour.nonghaeng.global.exception.dto.ErrorReason;

@AllArgsConstructor
@Getter
public enum PhotoErrorCode implements BaseErrorCode{
    DEFAULT_PHOTO_ERROR(HttpStatus.BAD_GATEWAY,"img_400_0","예약 기본 오류"),

    NO_OWNER_AUTHORIZATION_ERROR(HttpStatus.BAD_GATEWAY, "img_000_2", "해당 사진에 대한 소유자가 아닙니다."),

    //tour_photo
    NO_EXIST_TOUR_PHOTO_BY_ID_ERROR(HttpStatus.BAD_GATEWAY, "img_000_1", "tourPhotoId에 해당하는 체험이 없습니다."),

    EMPTY_PHOTO_LIST_ERROR(HttpStatus.BAD_GATEWAY, "img_404_2", "사진이 존재하지 않습니다."),

    WRONG_NUM_OF_REPRESENTATIVE_PHOTO_ERROR(HttpStatus.BAD_GATEWAY, "img_404_1", "대표사진이 너무 많습니다."),


    ;
    private HttpStatus status;
    private String code;
    private String reason;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(status.value())
                .code(this.code)
                .reason(this.reason)
                .build();
    }
}
