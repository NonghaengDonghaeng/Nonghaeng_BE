package tour.nonghaeng.domain.etc.roomType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomType {
    VILLAGE("1","마을숙박"),
    BED("2","민박숙박"),
    CAMPING("3","캠핑"),
    ETC("4","그 외"),
    //독체, 빌라 이런것도 추가할지 고민중
    ;

    private final String name;
    private final String code;

    public static RoomType ofCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException();
        }
        for (RoomType rt : RoomType.values()) {
            if (rt.getCode().equals(code)) {
                return rt;
            }
        }
        throw new IllegalArgumentException("일치하는 숙소코드가 없습니다.");
    }

}
