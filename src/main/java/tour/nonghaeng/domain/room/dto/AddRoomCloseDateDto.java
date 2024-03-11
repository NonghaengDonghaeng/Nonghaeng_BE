package tour.nonghaeng.domain.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.entity.RoomCloseDate;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AddRoomCloseDateDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate closeDate;

    @Builder
    public AddRoomCloseDateDto(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public RoomCloseDate toEntity(Room room) {
        return RoomCloseDate.builder()
                .room(room)
                .closeDate(this.closeDate)
                .build();
    }
}
