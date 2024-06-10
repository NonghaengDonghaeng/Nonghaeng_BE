package tour.nonghaeng.domain.reservation.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.service.SubReservationService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SubReservationServiceRegistry {

    private final Map<ReservationServiceType, SubReservationService<?,?,?>> serviceMap;

    public SubReservationServiceRegistry(List<SubReservationService<?,?,?>> serviceList) {
        this.serviceMap = serviceList.stream().collect(Collectors.toMap(SubReservationService::getType, Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public <SubReservation extends Reservation, Entity, DtoType extends CreateReservationDto<SubReservation, Entity>>
    SubReservationService<SubReservation, Entity, DtoType> getServiceByType(String type) {
        ReservationServiceType reservationServiceType = ReservationServiceType.ofDtype(type);
        return (SubReservationService<SubReservation, Entity, DtoType>) serviceMap.getOrDefault(reservationServiceType,null);
    }

    @SuppressWarnings("unchecked")
    public <SubReservation extends Reservation, Entity, DtoType extends CreateReservationDto<SubReservation, Entity>>
    SubReservationService<SubReservation, Entity, DtoType> getServiceByDto(DtoType createReservationDto) {
        return (SubReservationService<SubReservation, Entity, DtoType>) serviceMap.get(getType(createReservationDto));
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
