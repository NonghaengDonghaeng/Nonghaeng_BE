package tour.nonghaeng.global.infra.enums.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tour.nonghaeng.global.infra.exception.GlobalException;
import tour.nonghaeng.global.infra.exception.error.GlobalErrorCode;

@AllArgsConstructor
@Getter
public enum PhotoType {
    TOUR("tours/","tour"),
    EXPERIENCE("experiences/","experience"),
    ROOM("rooms/","room"),
    REVIEW("reviews/","review"),
    ;

    private final String folderName;
    private final String dtype;

    public static PhotoType ofDtype(String dtype) {
        if (dtype == null) {
            throw new GlobalException(GlobalErrorCode.CODE_IS_NULL_ERROR);
        }
        for (PhotoType pt : PhotoType.values()) {
            if (pt.getDtype().equals(dtype)) {
                return pt;
            }
        }
        throw new GlobalException(GlobalErrorCode.NO_MATCH_PHOTO_TYPE);
    }
}
