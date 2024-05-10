package tour.nonghaeng.domain.review.dto.exp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.review.entity.ExperienceReview;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateExpReviewDto {
    private Long expId;
    private String title;
    private String content;

    @Builder
    private CreateExpReviewDto(Long expId, String title, String content) {
        this.expId = expId;
        this.title = title;
        this.content = content;
    }

    public ExperienceReview toEntity(User user, Reservation reservation, Experience experience){

        String formatTitle = "[" + experience.getTour().getName() + "] " + this.title;

        return ExperienceReview.builder()
                .user(user)
                .reservation(reservation)
                .experience(experience)
                .title(formatTitle)
                .content(this.content)
                .build();
    }
}
