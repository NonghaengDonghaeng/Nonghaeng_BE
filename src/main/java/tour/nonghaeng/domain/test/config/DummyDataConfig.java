package tour.nonghaeng.domain.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tour.nonghaeng.domain.test.service.DummyDataManyService;
import tour.nonghaeng.domain.test.service.DummyDataService;

@Configuration
public class DummyDataConfig {
    @Bean
    public DummyDataService dummyDataService(DummyDataManyService dummyDataManyService) {
        return dummyDataManyService;
    }
}
