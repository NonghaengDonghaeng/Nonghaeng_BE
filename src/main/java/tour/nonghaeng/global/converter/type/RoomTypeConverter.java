package tour.nonghaeng.global.converter.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.room.RoomType;

@Component
public class RoomTypeConverter implements Converter<String, RoomType> {

    @Override
    public RoomType convert(String code) {
        return RoomType.ofCode(code);
    }
}
