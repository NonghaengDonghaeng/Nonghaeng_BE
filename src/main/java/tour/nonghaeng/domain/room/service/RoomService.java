package tour.nonghaeng.domain.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.dto.*;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.RoomErrorCode;
import tour.nonghaeng.global.validation.room.RoomValidator;
import tour.nonghaeng.global.validation.tour.TourValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomCloseDateService roomCloseDateService;
    private final TourService tourService;

    private final RoomValidator roomValidator;
    private final TourValidator tourValidator;


    public Long createAndAddRoom(Seller seller, CreateRoomDto dto) {

        //TODO: dto 검증

        Room room = dto.toEntity(tourService.findBySeller(seller));

        return roomRepository.save(room).getId();
    }

    public Page<RoomTourSummaryDto> getRoomTourSummaryDtoPage(Pageable pageable) {

        Page<Tour> tourPage = tourService.findAllTourPageWithRoom(pageable);

        tourValidator.pageValidate(tourPage);

        Page<RoomTourSummaryDto> dto = tourPage.map(tour ->
                RoomTourSummaryDto.toDto(tour, findMinPriceByTour(tour), findMaxPriceByTour(tour)));

        return dto;
    }

    public List<RoomSummaryDto> getRoomSummaryDtoList(Long tourId, LocalDate date, int numOfRoom) {

        Tour tour = tourService.findById(tourId);

        roomValidator.showRoomSummaryRequestParamValidate(tour.getRooms(),date);

        List<RoomSummaryDto> dtoList = tour.getRooms().stream()
                .map(room -> RoomSummaryDto.toDto(room)).toList();

        //TODO: 예약에서 날짜와 roomId를 통해 잔여 객실수를 기존 객실수에서 빼기

        List<RoomSummaryDto> filterList = dtoList.stream().filter(roomSummaryDto -> roomSummaryDto.getCurrentNumOfRoom() >= numOfRoom)
                .toList();

        roomValidator.roomConditionValidate(filterList);

        return filterList;
    }

    private int findMinPriceByTour(Tour tour) {
        return roomRepository.findMinPriceByTour(tour).intValue();
    }

    private int findMaxPriceByTour(Tour tour) {
        return roomRepository.findMaxPriceByTour(tour).intValue();
    }

    public RoomTourDetailDto getRoomTourDetailDto(Long tourId) {

        RoomTourDetailDto dto = RoomTourDetailDto.toDto(tourService.findById(tourId));

        dto.addRoomSummaryDtoList(getRoomSummaryDtoList(tourId, LocalDate.now(), 1));

        return dto;
    }

    public RoomDetailDto getRoomDetailDto(Long roomId,LocalDate requestDate) {

        Room room = findById(roomId);

        roomValidator.getRoomDetailDtoValidate(room, requestDate);

        RoomDetailDto dto = RoomDetailDto.toDto(room);

        //날짜를 인자로 예약을 통해 현재 잔여 객실 수 설정하기
        int reservedNumOfRoom = 1;
        dto.setCurrentNumOfRoom(reservedNumOfRoom);

        return dto;
    }

    public Long addOnlyCloseDates(Long roomId, List<AddRoomCloseDateDto> dtoList) {

        Room room = findById(roomId);

        roomCloseDateService.addCloseDates(room, dtoList);

        return roomRepository.save(room).getId();
    }

    public Room findById(Long roomId) {

        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomException(RoomErrorCode.NO_EXIST_ROOM_BY_ROOM_ID_ERROR));
    }

    public List<Room> findAll() {

        return roomRepository.findAll();
    }

    public List<Long> findAllIds() {
        return roomRepository.findAllIds();
    }

    public void checkOldestCloseDatePastOrNot(Long roomId) {

        Optional<LocalDate> oldestCloseDate = roomRepository.findOldestCloseDate(roomId);

        oldestCloseDate.ifPresent(localDate -> {
            if(localDate.isBefore(LocalDate.now())){
                Room room = findById(roomId);
                log.info("{} 날짜 오늘({})이 지나서 삭제", localDate, LocalDate.now());
                room.removeCloseDate(
                        roomCloseDateService
                                .findByRoomAndCloseDate(room, localDate));
                roomRepository.save(room);
            }
        });
    }
}

