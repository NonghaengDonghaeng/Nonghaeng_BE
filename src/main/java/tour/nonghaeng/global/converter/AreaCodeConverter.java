package tour.nonghaeng.global.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.area.AreaCode;

@Component
public class AreaCodeConverter implements Converter<String, AreaCode> {

    @Override
    public AreaCode convert(String code) {
        return AreaCode.ofCode(code);
    }

}
