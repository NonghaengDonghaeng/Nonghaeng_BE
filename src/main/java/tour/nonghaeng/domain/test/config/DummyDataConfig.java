package tour.nonghaeng.domain.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tour.nonghaeng.domain.test.service.DummyDataService;
import tour.nonghaeng.domain.test.service.DummyDataWellService;

@Configuration
public class DummyDataConfig {
    @Bean
    public DummyDataService dummyDataService(DummyDataWellService dummyDataWellService) {
        return dummyDataWellService;
    }
}
