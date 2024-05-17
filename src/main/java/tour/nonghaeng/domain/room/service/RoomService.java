package tour.nonghaeng.domain.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.room.RoomType;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.reservation.repo.RoomReservationRepository;
import tour.nonghaeng.domain.room.dto.*;
import tour.nonghaeng.domain.room.dto.specification.RoomSpecification;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.exception.RoomException;
import tour.nonghaeng.domain.room.exception.error.RoomErrorCode;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.room.valid.RoomValidator;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.domain.tour.valid.TourValidator;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomReservationRepository roomReservationRepository;

    private final RoomCloseDateService roomCloseDateService;
    private final TourService tourService;

    private final RoomValidator roomValidator;
    private final TourValidator tourValidator;




    public Long createAndAddRoom(Member seller, CreateRoomDto dto) {

        //TODO: dto 검증

        Room room = dto.toEntity(tourService.findBySeller(seller));

        return roomRepository.save(room).getId();
    }



    public Page<RoomTourSummaryDto> getRoomTourSummaryDtoPage(Pageable pageable, String keyword, AreaCode areaCode, RoomType roomType) {

        Page<Tour> tourPage = getTourPageWithRoomSpec(pageable, keyword, areaCode, roomType);

        tourValidator.pageValidate(tourPage);

        return tourPage.map(tour ->
                RoomTourSummaryDto.toDto(tour, findMinPriceByTour(tour), findMaxPriceByTour(tour)));
    }

    private Page<Tour> getTourPageWithRoomSpec(Pageable pageable, String keyword, AreaCode areaCode, RoomType roomType) {

        Specification<Room> roomSpec = RoomSpecification.buildRoomSpecification(keyword, areaCode, roomType);

        List<Room> rooms = roomRepository.findAll(roomSpec);

        Set<Tour> uniqueTours = new HashSet<>();

        for (Room room : rooms) {
            Tour tour = room.getTour();
            uniqueTours.add(tour);
        }

        return new PageImpl<>(new ArrayList<>(uniqueTours), pageable, uniqueTours.size());
    }




    public List<RoomSummaryDto> getRoomSummaryDtoList(Long tourId, LocalDate startDate, LocalDate endDate, int numOfRoom) {

        Tour tour = tourService.findById(tourId);

        List<LocalDate> dates = toLocalDateList(startDate, endDate);

        List<Room> rooms = tour.getRooms();

        Map<Long, Integer> remainOfNumMap = getRemainOfNumMap(rooms, dates);

        List<RoomSummaryDto> filterRoomList = rooms.stream()
                .filter(room -> remainOfNumMap.get(room.getId()) >= numOfRoom)
                .map(room -> RoomSummaryDto.toDto(room, remainOfNumMap.get(room.getId())))
                .toList();

        roomValidator.roomConditionValidate(filterRoomList);

        return filterRoomList;
    }

    private List<LocalDate> toLocalDateList(LocalDate startDate, LocalDate endDate) {

        List<LocalDate> dates = new ArrayList<>();

        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate.minusDays(1))) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }

    private Map<Long, Integer> getRemainOfNumMap(List<Room> rooms, List<LocalDate> dates) {

        Map<Long,Integer> totalOfNumMap = new HashMap<>();
        Map<Long,Integer> remainOfNumMap = new HashMap<>();

        rooms.forEach(room -> totalOfNumMap.put(room.getId(), room.getNumOfRoom()));
        rooms.forEach(room -> remainOfNumMap.put(room.getId(), room.getNumOfRoom()));


        for(LocalDate date : dates) {

            roomValidator.showRoomSummaryRequestParamValidate(rooms,date);

            rooms.forEach(room->{
                Integer newRemainNum = totalOfNumMap.get(room.getId())
                        - roomReservationRepository.countByRoomAndReservationDate(room, date).orElse(0);

                if (newRemainNum < remainOfNumMap.get(room.getId())) {
                    remainOfNumMap.replace(room.getId(), newRemainNum);
                }
            });
        }
        return remainOfNumMap;
    }



    private int findMinPriceByTour(Tour tour) {
        return roomRepository.findMinPriceByTour(tour);
    }

    private int findMaxPriceByTour(Tour tour) {
        return roomRepository.findMaxPriceByTour(tour);
    }



    public RoomTourDetailDto getRoomTourDetailDto(Long tourId) {

        RoomTourDetailDto dto = RoomTourDetailDto.toDto(tourService.findById(tourId));

        dto.addRoomSummaryDtoList(getRoomSummaryDtoList(tourId, LocalDate.now(),LocalDate.now().plusDays(1), 1));

        return dto;
    }



    public RoomDetailDto getRoomDetailDto(Long roomId,LocalDate requestDate) {

        Room room = findById(roomId);

        roomValidator.getRoomDetailDtoValidate(room, requestDate);

        RoomDetailDto dto = RoomDetailDto.toDto(room);

        //날짜를 인자로 예약을 통해 현재 잔여 객실 수 설정하기

        int reservedNumOfRoom = roomReservationRepository.countByRoomAndReservationDate(room, requestDate).orElse(0);
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

