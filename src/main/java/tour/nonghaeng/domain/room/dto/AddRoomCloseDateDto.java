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
import java.util.Objects;

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

    //TODO: eqauls 함수를 구현한 이유
    // RoomCloseDateValidator에서 stream().distinct().count()를 통해 중복이 있나 찾아낸다.
    // record는 자동생성이지만 클래스는 자동생성이 아니라 같은 날짜라도 new를 두번되면 다른 객체로 보기 때문에
    // 같은 날짜인 경우 같은 equals함수가 true로 되서 중복처리되도록 equals 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddRoomCloseDateDto that = (AddRoomCloseDateDto) o;

        return Objects.equals(closeDate, that.closeDate);
    }

    @Override
    public int hashCode() {
        return closeDate != null ? closeDate.hashCode() : 0;
    }
}
