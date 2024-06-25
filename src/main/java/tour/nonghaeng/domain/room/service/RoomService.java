package tour.nonghaeng.domain.room.service;

import tour.nonghaeng.domain.room.data.Room;
import tour.nonghaeng.domain.room.dto.*;
import tour.nonghaeng.global.infra.service.CrudService;
import tour.nonghaeng.global.infra.service.LikesService;
import tour.nonghaeng.global.infra.service.ManageCloseDateService;
import tour.nonghaeng.global.infra.service.ViewService;

import java.time.LocalDate;
import java.util.List;

public interface RoomService extends CrudService<Room, CreateRoomDto>, ViewService<RoomTourSummaryDto, RoomTourDetailDto, RoomSpecDto>, ManageCloseDateService<AddRoomCloseDateDto>, LikesService<Room> {

    List<RoomSummaryDto> getRoomSummaryDtoList(Long tourId, LocalDate startDate, LocalDate endDate, int numOfRoom);

    RoomDetailDto getRoomDetailDto(Long roomId, LocalDate requestDate);


}
