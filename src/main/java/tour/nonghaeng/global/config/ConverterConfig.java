package tour.nonghaeng.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tour.nonghaeng.global.converter.dto.CreateRoomReservationDtoConverter;
import tour.nonghaeng.global.converter.type.*;

import java.util.List;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    private final BankCodeConverter bankCodeConverter;
    private final AreaCodeConverter areaCodeConverter;
    private final TourTypeConverter tourTypeConverter;
    private final ExperienceTypeConverter experienceTypeConverter;
    private final RoomTypeConverter roomTypeConverter;
    private final CreateRoomReservationDtoConverter createRoomReservationDtoConverter;


    public ConverterConfig(BankCodeConverter bankCodeConverter,
                           AreaCodeConverter areaCodeConverter,
                           TourTypeConverter tourTypeConverter,
                           ExperienceTypeConverter experienceTypeConverter,
                           RoomTypeConverter roomTypeConverter,
                           CreateRoomReservationDtoConverter createRoomReservationDtoConverter) {
        this.bankCodeConverter = bankCodeConverter;
        this.areaCodeConverter = areaCodeConverter;
        this.tourTypeConverter = tourTypeConverter;
        this.experienceTypeConverter = experienceTypeConverter;
        this.roomTypeConverter = roomTypeConverter;
        this.createRoomReservationDtoConverter = createRoomReservationDtoConverter;
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(bankCodeConverter);
        registry.addConverter(areaCodeConverter);
        registry.addConverter(tourTypeConverter);
        registry.addConverter(experienceTypeConverter);
        registry.addConverter(roomTypeConverter);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(createRoomReservationDtoConverter);
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

}
