package tour.nonghaeng.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tour.nonghaeng.global.converter.AreaCodeConverter;
import tour.nonghaeng.global.converter.BankCodeConverter;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    private final BankCodeConverter bankCodeConverter;
    private final AreaCodeConverter areaCodeConverter;

    public ConverterConfig(BankCodeConverter bankCodeConverter,
                           AreaCodeConverter areaCodeConverter) {
        this.bankCodeConverter = bankCodeConverter;
        this.areaCodeConverter = areaCodeConverter;
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(bankCodeConverter);
        registry.addConverter(areaCodeConverter);
    }
}
