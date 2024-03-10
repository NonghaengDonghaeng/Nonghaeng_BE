package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExpRoundInfoDto {

    //TODO: 회차 정보 보여줄때 추가할 정보 있으면 추가하기
    private Long experienceId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkDate;
    private int numOfRound;
    private List<RoundInfo> content = new ArrayList<>();


    @Builder
    public ExpRoundInfoDto(Long experienceId,LocalDate checkDate) {
        this.experienceId = experienceId;
        this.checkDate = checkDate;
        this.numOfRound = 0;
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Builder
    @Getter
    public static class RoundInfo {
        private Long roundId;
        private LocalTime startTime;
        private LocalTime endTime;
        private int remainParticipant;

        public static RoundInfo convert(ExperienceRound round) {
            return RoundInfo.builder()
                    .roundId(round.getId())
                    .startTime(round.getStartTime())
                    .endTime(round.getEndTime())
                    .build();

        }

        public void setRemainParticipant(int remainParticipant) {
            this.remainParticipant = remainParticipant;
        }

    }

    public void addRoundInfo(RoundInfo roundInfo) {
        this.content.add(roundInfo);
        this.numOfRound++;
    }

}
