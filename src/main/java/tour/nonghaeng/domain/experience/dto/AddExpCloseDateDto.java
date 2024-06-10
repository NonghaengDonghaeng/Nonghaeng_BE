package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.experience.data.ExperienceCloseDate;
import tour.nonghaeng.global.infra.dto.AddCloseDateDto;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class AddExpCloseDateDto extends AddCloseDateDto {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate closeDate;

        public AddExpCloseDateDto(LocalDate closeDate) {
                this.closeDate = closeDate;
        }

        public ExperienceCloseDate toEntity(Experience experience) {
                return ExperienceCloseDate.builder()
                        .experience(experience)
                        .closeDate(this.closeDate)
                        .build();
        }
}
