package tour.nonghaeng.domain.trip.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.experience.dto.ExpSummaryDto;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.room.dto.RoomTourSummaryDto;
import tour.nonghaeng.domain.room.service.RoomService;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.domain.trip.dto.TripResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TripService {

    private final TourService tourService;
    private final ExperienceService experienceService;
    private final RoomService roomService;

    public TripResponseDto getTripResponseDto() {

        PageRequest pageRequest = PageRequest.of(0, 4);

        List<TourSummaryDto> tourSummaryDtoList = tourService.getTourSummaryDtoPage(pageRequest, null, null, null).getContent();
        List<ExpSummaryDto> expSummaryDtoList = experienceService.getExpSummaryDtoPage(pageRequest, null, null, null).getContent();
        List<RoomTourSummaryDto> roomTourSummaryDtoList = roomService.getRoomTourSummaryDtoPage(pageRequest, null, null, null).getContent();


        return TripResponseDto.builder()
                .tourSummaryDtoList(tourSummaryDtoList)
                .roomTourSummaryDtoList(roomTourSummaryDtoList)
                .expSummaryDtoList(expSummaryDtoList)
                .build();
    }

}
