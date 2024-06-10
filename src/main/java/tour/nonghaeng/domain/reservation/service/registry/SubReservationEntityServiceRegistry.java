package tour.nonghaeng.domain.reservation.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.service.SubReservationEntityService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SubReservationEntityServiceRegistry {

    private final Map<ReservationServiceType, SubReservationEntityService<?,?,?>> serviceMap;

    public SubReservationEntityServiceRegistry(List<SubReservationEntityService<?,?,?>> serviceList) {
        this.serviceMap = serviceList.stream().collect(Collectors.toMap(SubReservationEntityService::getType, Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public <SubReservation extends Reservation, Entity, DtoType extends CreateReservationDto<SubReservation, Entity>>
    SubReservationEntityService<SubReservation, Entity, DtoType> getServiceByType(String type) {
        ReservationServiceType reservationServiceType = ReservationServiceType.ofDtype(type);
        return (SubReservationEntityService<SubReservation, Entity, DtoType>) serviceMap.get(reservationServiceType);
    }

    @SuppressWarnings("unchecked")
    public <SubReservation extends Reservation, Entity, DtoType extends CreateReservationDto<SubReservation, Entity>>
    SubReservationEntityService<SubReservation, Entity, DtoType> getServiceByDto(DtoType createReservationDto) {
        return (SubReservationEntityService<SubReservation, Entity, DtoType>) serviceMap.get(getType(createReservationDto));
    }

    private ReservationServiceType getType(CreateReservationDto<?, ?> createReservationDto) {
        if (createReservationDto instanceof CreateRoomReservationDto) {
            return ReservationServiceType.ROOM;
        } else if (createReservationDto instanceof CreateExpReservationDto) {
            return ReservationServiceType.EXPERIENCE;
        }
        throw new RuntimeException();
    }

}
