package tour.nonghaeng.domain.reservation.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.service.CrudReservationService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CrudReservationServiceRegistry {

    private final Map<ReservationServiceType, CrudReservationService> serviceMap;

    public CrudReservationServiceRegistry(List<CrudReservationService> serviceList) {
        this.serviceMap = serviceList.stream().collect(Collectors.toMap(CrudReservationService::getType, Function.identity()));
    }

    public CrudReservationService getServiceByType(String type) {
        return serviceMap.get(ReservationServiceType.ofDtype(type));
    }

    public CrudReservationService getServiceByDto(CreateReservationDto createReservationDto) {
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
