package tour.nonghaeng.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.experience.data.ExperienceRound;
import tour.nonghaeng.domain.experience.service.ExperienceRoundService;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.member.service.UserService;
import tour.nonghaeng.domain.reservation.data.ExperienceReservation;
import tour.nonghaeng.domain.reservation.data.Reservation;
import tour.nonghaeng.domain.reservation.data.repo.ExperienceReservationRepository;
import tour.nonghaeng.domain.reservation.dto.CreateReservationDto;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.presentation.exception.ReservationException;
import tour.nonghaeng.domain.reservation.presentation.exception.error.ReservationErrorCode;
import tour.nonghaeng.domain.reservation.service.valid.ExperienceReservationValidator;
import tour.nonghaeng.global.auth.AuthValidator;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExperienceReservationService implements CrudReservationService {

    private final ExperienceReservationRepository experienceReservationRepository;

    private final ExperienceRoundService experienceRoundService;
    private final UserService userService;

    private final ExperienceReservationValidator experienceReservationValidator;
    private final AuthValidator authValidator;


    @Override
    public ReservationServiceType getType() {
        return ReservationServiceType.EXPERIENCE;
    }

    @Override
    public ExperienceReservation findById(Long experienceReservationId) {

        return experienceReservationRepository.findById(experienceReservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID));
    }

    @Override
    public ExperienceReservation create(Member member, CreateReservationDto createDto) {
        User user = authValidator.userValidate(member);

        CreateExpReservationDto createDto1 = (CreateExpReservationDto) createDto;

        ExperienceRound experienceRound = experienceRoundService.findById(createDto1.getRoundId());

        experienceReservationValidator
                .experienceReservationValidate(
                        experienceRound, user,
                        countRemainOfParticipant(experienceRound, createDto1.getReservationDate()),
                        createDto1);

        userService.payPoint(user, createDto1.getFinalPrice());

        return experienceReservationRepository.save(createDto1.toEntity(user, experienceRound));
    }


    //해당 날짜, 해당 회차에 잔여인원 구하기
    public int countRemainOfParticipant(ExperienceRound experienceRound, LocalDate localDate) {

        int currentReservationParticipant = 0;

        Optional<Integer> currentNum =
                experienceReservationRepository.countParticipantByExperienceRoundAndReservationDate(experienceRound, localDate);

        if (currentNum.isPresent()){
            currentReservationParticipant = currentNum.get();
        }

        return experienceRound.getMaxParticipant() - currentReservationParticipant;
    }

    public Page<Reservation> findReservationPageByUser(Member user, Pageable pageable) {

        return experienceReservationRepository.findReservationPageByUser(authValidator.userValidate(user), pageable);
    }


    public Page<Reservation> findReservationPageBySeller(Member seller, Pageable pageable) {

        return experienceReservationRepository.findReservationPageBySeller(authValidator.sellerValidate(seller), pageable);
    }



    public LocalDate findEndDateById(Long reservationId) {

        return experienceReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID))
                .getReservationDate();
    }


}
