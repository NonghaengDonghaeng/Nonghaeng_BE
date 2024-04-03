package tour.nonghaeng.domain.etc.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PhotoType {
    TOUR("tours/","tour"),
    EXPERIENCE("experiences/","experience"),
    ROOM("rooms/","room"),
    ;

    private final String folderName;
    private final String dtype;

    public static PhotoType ofDtype(String dtype) {
        if (dtype == null) {
            throw new IllegalArgumentException();
        }
        for (PhotoType pt : PhotoType.values()) {
            if (pt.getDtype().equals(dtype)) {
                return pt;
            }
        }
        throw new IllegalArgumentException("일치하는 사진타입이 없습니다.");
    }
}
