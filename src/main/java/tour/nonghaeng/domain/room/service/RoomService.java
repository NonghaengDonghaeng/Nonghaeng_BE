package tour.nonghaeng.domain.room.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.dto.CreateRoomDto;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.domain.tour.service.TourService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomService {

    private final TourService tourService;

    private final RoomRepository roomRepository;

    public Long createAndAddRoom(Seller seller,CreateRoomDto dto) {
        //검증

        Tour tour = tourService.findBySeller(seller);
        Room room = dto.toEntity(seller, tour);

        return roomRepository.save(room).getId();
    }
}
