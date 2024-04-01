package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.ReservationSellerDetailDto;
import tour.nonghaeng.domain.reservation.dto.ReservationSellerSummaryDto;
import tour.nonghaeng.domain.reservation.dto.ReservationUserDetailDto;
import tour.nonghaeng.domain.reservation.dto.ReservationUserSummaryDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationSellerSummaryDto;
import tour.nonghaeng.domain.reservation.dto.exp.ExpReservationUserSummaryDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationSellerSummaryDto;
import tour.nonghaeng.domain.reservation.dto.room.RoomReservationUserSummaryDto;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;
import tour.nonghaeng.domain.reservation.entity.Reservation;
import tour.nonghaeng.domain.reservation.repo.ExperienceReservationRepository;
import tour.nonghaeng.domain.reservation.repo.ReservationRepository;
import tour.nonghaeng.domain.reservation.repo.RoomReservationRepository;
import tour.nonghaeng.global.validation.reservation.ExperienceReservationValidator;
import tour.nonghaeng.global.validation.reservation.ReservationValidator;
import tour.nonghaeng.global.validation.reservation.RoomReservationValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomReservationRepository roomReservationRepository;
    private final ExperienceReservationRepository experienceReservationRepository;

    private final ExperienceReservationService experienceReservationService;

    private final RoomReservationValidator roomReservationValidator;
    private final ExperienceReservationValidator experienceReservationValidator;
    private final ReservationValidator reservationValidator;

    public Page<? extends ReservationUserSummaryDto> getReservationUserSummaryDtoPage(User user, Pageable pageable,String type) {

        Page<Reservation> reservationPage = findReservationPageByUser(user, pageable, type);

        reservationValidator.pageValidate(reservationPage);
        //여기서 toDto 는 오버라이딩된 toDto 함수 사용됨
        Page<ReservationUserSummaryDto> dtoPage = reservationPage.map(reservation -> reservation.toUserSummaryDto());

        return downCastingUserSummaryDto(dtoPage);
    }

    public ReservationUserDetailDto getReservationUserDetailDto(Long reservationId) {

        reservationValidator.idValidate(reservationId);

        Reservation reservation = findByReservationId(reservationId);

        return reservation.toUserDetailDto();
    }

    public Page<? extends ReservationSellerSummaryDto> getReservationSellerSummaryDtoPage(Seller seller, Pageable pageable,String type) {

        Page<Reservation> reservationPage = findReservationPageBySeller(seller, pageable, type);

        reservationValidator.pageValidate(reservationPage);

        Page<ReservationSellerSummaryDto> dtoPage = reservationPage.map(reservation -> reservation.toSellerSummaryDto());

        return downCastingSellerSummaryDto(dtoPage);
    }

    public ReservationSellerDetailDto getReservationSellerDetailDto(Long reservationId) {

        reservationValidator.idValidate(reservationId);

        Reservation reservation = findByReservationId(reservationId);

        int remainOfParticipant = 0;

        if (reservation instanceof ExperienceReservation) {
            ExperienceReservation experienceReservation = (ExperienceReservation) reservation;
            remainOfParticipant = experienceReservationService.countRemainOfParticipant(experienceReservation.getExperienceRound(), experienceReservation.getReservationDate());
        }

        return reservation.toSellerDetailDto(remainOfParticipant);
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

    private Page<? extends ReservationSellerSummaryDto> downCastingSellerSummaryDto(Page<ReservationSellerSummaryDto> dtoPage) {

        List<? extends ReservationSellerSummaryDto> filteredList = dtoPage.getContent().stream()
                .map(dto -> {
                    if (dto instanceof RoomReservationSellerSummaryDto) {
                        return (RoomReservationSellerSummaryDto) dto;
                    } else {
                        return (ExpReservationSellerSummaryDto) dto;
                    }
                })
                .toList();

        return new PageImpl<>(filteredList, dtoPage.getPageable(), dtoPage.getTotalElements());
    }

    private Page<Reservation> findReservationPageByUser(User user, Pageable pageable,String type) {

        if (type.equals("room")) {

            return roomReservationRepository.findReservationAllByUser(user, pageable);
        }
        return experienceReservationRepository.findReservationAllByUser(user, pageable);
    }

    private Page<Reservation> findReservationPageBySeller(Seller seller, Pageable pageable, String type) {

        if (type.equals("room")) {

            return roomReservationRepository.findReservationAllBySeller(seller, pageable);
        }
        return experienceReservationRepository.findReservationAllBySeller(seller, pageable);
    }

    private Reservation findByReservationId(Long reservationId) {

        String reservationType = reservationRepository.findReservationType(reservationId);

        if (reservationType.equals("RoomReservation")) {
            return roomReservationRepository.findReservationById(reservationId);
        }

        return experienceReservationRepository.findReservationById(reservationId);
    }



}
