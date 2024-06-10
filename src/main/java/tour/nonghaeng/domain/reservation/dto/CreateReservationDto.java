package tour.nonghaeng.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.global.infra.dto.CreateDto;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateRoomReservationDto.class, name = "room"),
        @JsonSubTypes.Type(value = CreateExpReservationDto.class, name = "experience")
})
public abstract class CreateReservationDto<SubReservation extends Reservation,Entity> extends CreateDto {
    public abstract SubReservation toEntity(User user, Entity entity);

}

