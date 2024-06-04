package tour.nonghaeng.domain.room.service;

import tour.nonghaeng.domain.room.data.Room;
import tour.nonghaeng.domain.room.dto.*;
import tour.nonghaeng.global.infra.service.CrudService;
import tour.nonghaeng.global.infra.service.ViewService;

import java.time.LocalDate;
import java.util.List;

public interface RoomService extends CrudService<Room, CreateRoomDto>, ViewService<RoomTourSummaryDto, RoomTourDetailDto, RoomSpecDto> {

    List<RoomSummaryDto> getRoomSummaryDtoList(Long tourId, LocalDate startDate, LocalDate endDate, int numOfRoom);

    RoomDetailDto getRoomDetailDto(Long roomId, LocalDate requestDate);

    Long addOnlyCloseDates(Long roomId, List<AddRoomCloseDateDto> dtoList);

    //스케줄러 관한
    List<Long> findAllIds();

    void checkOldestCloseDatePastOrNot(Long roomId);

}
