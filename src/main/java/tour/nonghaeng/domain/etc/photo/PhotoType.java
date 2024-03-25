package tour.nonghaeng.domain.etc.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PhotoType {
    TOUR("tours/"),
    EXPERIENCE("experiences/"),
    ROOM("rooms/"),
    ;

    private final String folderName;
}
