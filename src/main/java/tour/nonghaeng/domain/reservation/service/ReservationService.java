package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.ReservationUserSummaryDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationUserSummaryDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationUserSummaryDto;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.reservation.repo.ExperienceReservationRepository;
import tour.nonghaeng.domain.reservation.repo.ReservationRepository;
import tour.nonghaeng.domain.reservation.repo.RoomReservationRepository;
import tour.nonghaeng.global.validation.reservation.ExperienceReservationValidator;
import tour.nonghaeng.global.validation.reservation.ReservationValidator;
import tour.nonghaeng.global.validation.reservation.RoomReservationValidator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomReservationRepository roomReservationRepository;
    private final ExperienceReservationRepository experienceReservationRepository;

    private final RoomReservationValidator roomReservationValidator;
    private final ExperienceReservationValidator experienceReservationValidator;
    private final ReservationValidator reservationValidator;

    public Page<? extends ReservationUserSummaryDto> getReservationUserSummaryDtoPage(User user, Pageable pageable,String type) {

        Page<Reservation> reservationPage = findReservationPage(user, pageable, type);
        //여기서 toDto 는 오버라이딩된 toDto 함수 사용됨
        Page<ReservationUserSummaryDto> dtoPage = reservationPage.map(reservation -> reservation.toUserSummaryDto());

        return downCastingUserSummaryDto(dtoPage);
    }

    private Page<? extends ReservationUserSummaryDto> downCastingUserSummaryDto(Page<ReservationUserSummaryDto> dtoPage) {

        List<? extends ReservationUserSummaryDto> filteredList = dtoPage.getContent().stream()
                .map(dto->{
                    if(dto instanceof RoomReservationUserSummaryDto){
                        return (RoomReservationUserSummaryDto) dto;
                    }else{
                        return (ExpReservationUserSummaryDto) dto;
                    }
                })
                .toList();

        return new PageImpl<>(filteredList, dtoPage.getPageable(), dtoPage.getTotalElements());
    }

    private Page<Reservation> findReservationPage(User user, Pageable pageable,String type) {

        if (type.equals("room")) {
            return roomReservationRepository.findReservationAllByUser(user, pageable);
        }
        return experienceReservationRepository.findReservationAllByUser(user, pageable);
    }

    private Reservation findByReservationId(Long reservationId) {
        Optional<Reservation> byId = reservationRepository.findById(reservationId);
        return byId.get();

        int i;



        int i = 1;


    }



}
