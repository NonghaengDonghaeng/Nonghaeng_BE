package tour.nonghaeng.global.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.tour.TourType;

@Component
public class TourTypeConverter implements Converter<String,TourType> {

    @Override
    public TourType convert(String code) {
        return TourType.ofCode(code);
    }
}
