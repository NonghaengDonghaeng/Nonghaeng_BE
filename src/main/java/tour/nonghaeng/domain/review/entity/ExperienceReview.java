package tour.nonghaeng.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.experience.dto.ExpSummaryDto;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.exp.ExpReviewDetailDto;
import tour.nonghaeng.domain.review.dto.exp.ExpReviewSummaryDto;

@Entity
@Table(name = "EXPERIENCE_REVIEWS")
@DiscriminatorValue("experience")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExperienceReview extends Review {

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    @Builder
    public ExperienceReview(User user, Reservation reservation, String title, String content, Experience experience) {
        super(user, reservation, title, content);
        this.experience = experience;
    }

    @Override
    public ReviewSummaryDto toReviewSummaryDto() {
        return ExpReviewSummaryDto.builder()
                .createDate(this.getCreatedAt().toLocalDate())
                .reviewId(this.getId())
                .expName(this.experience.getExperienceName())
                .author(this.getUser().getName())
                .title(this.getTitle())
                .type("experience")
                .photoInfoDto(super.findRepresentPhoto().isPresent() ?
                        PhotoInfoDto.toDto(this.findRepresentPhoto().get()) : null)
                .build();
    }

    @Override
    public ReviewDetailDto toReviewDetailDto() {
        return ExpReviewDetailDto.builder()
                .createDate(this.getCreatedAt().toLocalDate())
                .expId(this.experience.getId())
                .expName(this.experience.getExperienceName())
                .author(this.getUser().getName())
                .title(this.getTitle())
                .content(this.getContent())
                .expSummaryDto(ExpSummaryDto.toDto(this.experience))
                .photoInfoDtoList(this.getReviewPhotos().stream()
                        .map(PhotoInfoDto::toDto).toList())
                .build();
    }
}
