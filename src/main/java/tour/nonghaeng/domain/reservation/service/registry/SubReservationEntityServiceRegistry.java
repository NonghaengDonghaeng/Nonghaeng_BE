package tour.nonghaeng.domain.reservation.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.service.SubReservationEntityService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SubReservationEntityServiceRegistry {

    private final Map<ReservationServiceType, SubReservationEntityService<Objects>> serviceMap;

    public SubReservationEntityServiceRegistry(List<SubReservationEntityService<Objects>> serviceList) {
        this.serviceMap = serviceList.stream().collect(Collectors.toMap(SubReservationEntityService::getType, Function.identity()));
    }

    public SubReservationEntityService<Objects> getServiceByType(String type) {

        ReservationServiceType reservationServiceType = ReservationServiceType.ofDtype(type);

        return serviceMap.getOrDefault(reservationServiceType, null);
    }

    public SubReservationEntityService<Objects> getServiceByDto(CreateReservationDto createReservationDto) {
        return serviceMap.get(getType(createReservationDto));
    }

    private ReservationServiceType getType(CreateReservationDto createReservationDto) {
        if (createReservationDto instanceof CreateRoomReservationDto) {
            return ReservationServiceType.ROOM;
        } else if (createReservationDto instanceof CreateExpReservationDto) {
            return ReservationServiceType.EXPERIENCE;
        }
        throw new RuntimeException();
    }
}
