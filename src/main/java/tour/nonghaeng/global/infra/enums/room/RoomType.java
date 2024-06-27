package tour.nonghaeng.global.infra.enums.room;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tour.nonghaeng.global.infra.exception.GlobalException;
import tour.nonghaeng.global.infra.exception.error.GlobalErrorCode;

@RequiredArgsConstructor
@Getter
public enum RoomType {
    VILLAGE("1","마을숙박"),
    BED("2","민박숙박"),
    CAMPING("3","캠핑"),
    ETC("4","그 외"),
    //독체, 빌라 이런것도 추가할지 고민중
    ;

    @JsonValue
    private final String code;
    private final String name;


    public static RoomType ofCode(String code) {
        if (code == null) {
            throw new GlobalException(GlobalErrorCode.CODE_IS_NULL_ERROR);
        }
        for (RoomType rt : RoomType.values()) {
            if (rt.getCode().equals(code)) {
                return rt;
            }
        }
        throw new GlobalException(GlobalErrorCode.NO_MATCH_ROOM_TYPE);
    }

}
