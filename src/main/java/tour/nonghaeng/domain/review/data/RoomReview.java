package tour.nonghaeng.domain.review.data;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.photo.dto.PhotoInfoDto;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.review.dto.ReviewDetailDto;
import tour.nonghaeng.domain.review.dto.ReviewSummaryDto;
import tour.nonghaeng.domain.review.dto.room.RoomReviewDetailDto;
import tour.nonghaeng.domain.review.dto.room.RoomReviewSummaryDto;
import tour.nonghaeng.domain.room.dto.RoomSummaryDto;
import tour.nonghaeng.domain.room.data.Room;

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
    private RoomReview(Room room, User user, Reservation reservation, String title, String content) {
        super(user, reservation, title, content);
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
                .content(this.getContent())
                .type("room")
                .likes(this.getReviewLikes().size())
                .photoInfoDto(super.findRepresentPhoto().isPresent() ?
                        PhotoInfoDto.toDto(this.findRepresentPhoto().get()) : null)
                .build();
    }

    //TODO: 추후에 RoomSummaryDto에 잔여객실수를 현재날짜로 구해서 보내주도록하기, 현재는 그냥 총객실수를 보내줌
    @Override
    public ReviewDetailDto toReviewDetailDto() {
        return RoomReviewDetailDto.builder()
                .createDate(this.getCreatedAt().toLocalDate())
                .roomId(this.getRoom().getId())
                .roomName(this.getRoom().getRoomName())
                .author(this.getUser().getName())
                .title(this.getTitle())
                .content(this.getContent())
                .likes(this.getReviewLikes().size())
                .roomSummaryDto(RoomSummaryDto.toDto(this.room,this.room.getNumOfRoom()))
                .photoInfoDtoList(this.getReviewPhotos().stream()
                        .map(PhotoInfoDto::toDto).toList())
                .build();
    }
}
