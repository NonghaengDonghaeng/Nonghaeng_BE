package tour.nonghaeng.global.converter.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;

@Component
public class AreaCodeConverter implements Converter<String, AreaCode> {

    @Override
    public AreaCode convert(String code) {
        return AreaCode.ofCode(code);
    }

}
