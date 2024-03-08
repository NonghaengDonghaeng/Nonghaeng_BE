package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;

import java.time.LocalTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddExpRoundDto(
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime startTime,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime endTime,
        int maxParticipant
) {
    private static final int DEFAULT_DURING_HOUR = 2;

    public ExperienceRound toEntity(Experience experience) {

        return ExperienceRound.builder()
                .experience(experience)
                .startTime(this.startTime)
                .endTime(this.endTime == null ? this.startTime.plusHours(DEFAULT_DURING_HOUR) : this.endTime)
                .maxParticipant(this.maxParticipant == 0 ? experience.getMaxParticipant() : this.maxParticipant)
                .build();
    }
}
