package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceOpenDate;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddExpOpenDateDto(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate openDate
) {
        public ExperienceOpenDate toEntity(Experience experience) {
                return ExperienceOpenDate.builder()
                        .experience(experience)
                        .openDate(this.openDate)
                        .build();
        }
}
