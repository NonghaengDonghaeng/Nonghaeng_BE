package tour.nonghaeng.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tour.nonghaeng.global.converter.*;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    private final BankCodeConverter bankCodeConverter;
    private final AreaCodeConverter areaCodeConverter;
    private final TourTypeConverter tourTypeConverter;
    private final ExperienceTypeConverter experienceTypeConverter;
    private final RoomTypeConverter roomTypeConverter;


    public ConverterConfig(BankCodeConverter bankCodeConverter,
                           AreaCodeConverter areaCodeConverter,
                           TourTypeConverter tourTypeConverter,
                           ExperienceTypeConverter experienceTypeConverter,
                           RoomTypeConverter roomTypeConverter) {
        this.bankCodeConverter = bankCodeConverter;
        this.areaCodeConverter = areaCodeConverter;
        this.tourTypeConverter = tourTypeConverter;
        this.experienceTypeConverter = experienceTypeConverter;
        this.roomTypeConverter = roomTypeConverter;
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(bankCodeConverter);
        registry.addConverter(areaCodeConverter);
        registry.addConverter(tourTypeConverter);
        registry.addConverter(experienceTypeConverter);
        registry.addConverter(roomTypeConverter);
    }
}
