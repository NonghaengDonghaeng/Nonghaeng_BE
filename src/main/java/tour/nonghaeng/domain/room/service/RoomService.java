package tour.nonghaeng.domain.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.dto.CreateRoomDto;
import tour.nonghaeng.domain.room.dto.RoomSummaryDto;
import tour.nonghaeng.domain.room.dto.RoomTourDetailDto;
import tour.nonghaeng.domain.room.dto.RoomTourSummaryDto;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.validation.RoomCloseDateValidator;
import tour.nonghaeng.global.validation.RoomValidator;
import tour.nonghaeng.global.validation.TourValidator;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomValidator roomValidator;
    private final RoomCloseDateValidator roomCloseDateValidator;
    private final TourValidator tourValidator;


    private final TourService tourService;


    public Long createAndAddRoom(Seller seller, CreateRoomDto dto) {
        //검증

        Tour tour = tourService.findBySeller(seller);
        Room room = dto.toEntity(seller, tour);

        return roomRepository.save(room).getId();
    }

    public Page<RoomTourSummaryDto> showTourRoomSummary(Pageable pageable) {

        Page<Tour> tourPage = tourService.findAllTourWithRoom(pageable);

        tourValidator.pageValidate(tourPage);

        Page<RoomTourSummaryDto> dto = tourPage.map(tour ->
                RoomTourSummaryDto.toDto(tour, findMinPriceByTour(tour), findMaxPriceByTour(tour)));

        return dto;


    }

    public List<RoomSummaryDto> showRoomSummaryList(Long tourId, LocalDate date, int numOfRoom) {

        Tour tour = tourService.findById(tourId);
        List<Room> rooms = tour.getRooms();

        roomValidator.showRoomSummaryRequestParamValidate(rooms,date,numOfRoom);

        List<RoomSummaryDto> dtoList = rooms.stream().map(room -> RoomSummaryDto.toDto(room)).toList();

        //예약에서 날짜와 roomId를 통해 잔여 객실수를 기존 객실수에서 빼기


        //그 이후 객실수와 필요한 객실수 비교 후 조건에 안맞으면 리스트 제외
        List<RoomSummaryDto> filterList = dtoList.stream().filter(roomSummaryDto -> roomSummaryDto.getNumOfRoom() >= numOfRoom)
                .toList();

        //그 이후 조건에 충족하는 방이 있는지 한번 더 검증
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
        Tour tour = tourService.findById(tourId);

        RoomTourDetailDto dto = RoomTourDetailDto.toDto(tour);

        dto.addRoomSummaryDtoList(showRoomSummaryList(tourId, LocalDate.now(), 1));

        return dto;
    }

}

