package tour.nonghaeng.domain.review.dto.room;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.review.entity.RoomReview;
import tour.nonghaeng.domain.room.entity.Room;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateRoomReviewDto {

    private Long roomId;
    private String title;
    private String content;

    @Builder
    private CreateRoomReviewDto(Long roomId, String title, String content) {
        this.roomId = roomId;
        this.title = title;
        this.content = content;
    }

    public RoomReview toEntity(User user, Room room) {

        return RoomReview.builder()
                .user(user)
                .room(room)
                .title(this.title)
                .content(this.content)
                .build();
    }

}
