package tour.nonghaeng.domain.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.dto.CreateRoomDto;
import tour.nonghaeng.domain.room.dto.TourRoomSummaryDto;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.validation.RoomValidator;
import tour.nonghaeng.global.validation.TourValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomValidator roomValidator;
    private final TourValidator tourValidator;

    private final TourService tourService;


    public Long createAndAddRoom(Seller seller,CreateRoomDto dto) {
        //검증

        Tour tour = tourService.findBySeller(seller);
        Room room = dto.toEntity(seller, tour);

        return roomRepository.save(room).getId();
    }

    public Page<TourRoomSummaryDto> showTourRoomSummary(Pageable pageable) {

        Page<Tour> tourPage = tourService.findTourWithRoom(pageable);

        tourValidator.pageValidate(tourPage);

        Page<TourRoomSummaryDto> dto = tourPage.map(tour ->
                TourRoomSummaryDto.toDto(tour, findMinPriceByTour(tour), findMaxPriceByTour(tour)));

        return dto;


    }

    private int findMinPriceByTour(Tour tour) {
        return roomRepository.findMinPriceByTour(tour).intValue();
    }

    private int findMaxPriceByTour(Tour tour) {
        return roomRepository.findMaxPriceByTour(tour).intValue();
    }

}

