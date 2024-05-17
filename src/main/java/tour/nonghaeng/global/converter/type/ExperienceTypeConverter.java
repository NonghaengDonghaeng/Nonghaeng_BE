package tour.nonghaeng.global.converter.type;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.experience.ExperienceType;

@Component
public class ExperienceTypeConverter implements Converter<String, ExperienceType> {

    @Override
    public ExperienceType convert(String code) {
        return ExperienceType.ofCode(code);
    }
}
