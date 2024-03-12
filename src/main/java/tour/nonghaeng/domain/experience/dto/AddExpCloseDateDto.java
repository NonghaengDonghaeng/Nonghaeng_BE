package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceCloseDate;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddExpCloseDateDto(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate closeDate
) {
        public ExperienceCloseDate toEntity(Experience experience) {
                return ExperienceCloseDate.builder()
                        .experience(experience)
                        .closeDate(this.closeDate)
                        .build();
        }
}
