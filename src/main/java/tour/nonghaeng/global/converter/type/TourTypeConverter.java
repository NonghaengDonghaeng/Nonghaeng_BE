package tour.nonghaeng.global.converter.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.tour.TourType;

@Component
public class TourTypeConverter implements Converter<String,TourType> {

    @Override
    public TourType convert(String code) {
        return TourType.ofCode(code);
    }
}
