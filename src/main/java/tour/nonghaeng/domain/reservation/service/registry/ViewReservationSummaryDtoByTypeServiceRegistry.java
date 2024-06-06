package tour.nonghaeng.domain.reservation.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.reservation.service.ViewReservationSummaryDtoByTypeService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ViewReservationSummaryDtoByTypeServiceRegistry {

    private final Map<ReservationServiceType, ViewReservationSummaryDtoByTypeService> serviceMap;

    public ViewReservationSummaryDtoByTypeServiceRegistry(List<ViewReservationSummaryDtoByTypeService> serviceList) {
        this.serviceMap = serviceList.stream().collect(Collectors.toMap(ViewReservationSummaryDtoByTypeService::getType, Function.identity()));
    }

    public ViewReservationSummaryDtoByTypeService getService(String type) {
        return serviceMap.get(ReservationServiceType.ofDtype(type));
    }
}
