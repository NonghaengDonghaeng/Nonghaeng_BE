package tour.nonghaeng.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.room.RoomReviewDetailDto;
import tour.nonghaeng.domain.review.dto.room.RoomReviewSummaryDto;
import tour.nonghaeng.domain.room.dto.RoomSummaryDto;
import tour.nonghaeng.domain.room.entity.Room;

@Entity
@Table(name="ROOM_REVIEWS")
@DiscriminatorValue("room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomReview extends Review{

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    private RoomReview(Room room, User user, String title, String content) {
        super(user,title,content);
        this.room = room;
    }

    @Override
    public ReviewSummaryDto toReviewSummaryDto() {
        return RoomReviewSummaryDto.builder()
                .createDate(this.getCreatedAt().toLocalDate())
                .reviewId(this.getId())
                .roomName(this.getRoom().getRoomName())
                .author(this.getUser().getName())
                .title(this.getTitle())
                .type("room")
                .photoInfoDto(this.findRepresentPhoto().isPresent() ?
                        PhotoInfoDto.toDto(this.findRepresentPhoto().get()) : null)
                .build();
    }

    @Override
    public ReviewDetailDto toReviewDetailDto() {
        return RoomReviewDetailDto.builder()
                .createDate(this.getCreatedAt().toLocalDate())
                .roomId(this.getRoom().getId())
                .roomName(this.getRoom().getRoomName())
                .author(this.getUser().getName())
                .title(this.getTitle())
                .content(this.getContent())
                .roomSummaryDto(RoomSummaryDto.toDto(this.room))
                .photoInfoDtoList(this.getRoom().getRoomPhotoList().stream()
                        .map(PhotoInfoDto::toDto)
                        .toList())
                .build();
    }
}
