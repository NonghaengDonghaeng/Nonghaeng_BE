package tour.nonghaeng.global.validation.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tour.nonghaeng.domain.etc.reservation.ReservationStateType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.entity.ExperienceRound;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.exp.CreateExpReservationDto;
import tour.nonghaeng.domain.reservation.entity.ExperienceReservation;
import tour.nonghaeng.domain.reservation.repo.ExperienceReservationRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.NongHaengException;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.validation.experience.ExperienceCloseDateValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static tour.nonghaeng.global.testEntity.experience.TestExperience.makeTestExperience;
import static tour.nonghaeng.global.testEntity.experience.TestExperienceRound.makeTestExperienceRound;
import static tour.nonghaeng.global.testEntity.reservation.TestExperienceReservation.makeTestExperienceReservation;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;
import static tour.nonghaeng.global.testEntity.user.TestUser.makeTestUser;

@ExtendWith(MockitoExtension.class)
@DisplayName("체험예약 검증 테스트")
class ExperienceReservationValidatorTest {

    @Mock
    private ExperienceReservationRepository experienceReservationRepository;

    @Mock
    private ExperienceCloseDateValidator experienceCloseDateValidator;

    @InjectMocks
    private ExperienceReservationValidator experienceReservationValidator;

    private static User user;
    private static Seller seller;
    private static Tour tour;
    private static Experience experience;
    private static ExperienceRound experienceRound;
    private static ExperienceReservation experienceReservation;
    private static LocalDate reservationDate;
    private static Long fakeReservationId;
    private static Long fakeRoundId;

    @BeforeEach
    void setUp() {
        user = makeTestUser();
        seller = makeTestSeller();
        tour = makeTestTour(seller);
        experience = makeTestExperience(tour);
        experienceRound = makeTestExperienceRound(experience, 10);
        experienceReservation = makeTestExperienceReservation(user, experienceRound, reservationDate);
        reservationDate = LocalDate.of(2024, 5, 5);
        fakeReservationId = 1L;
        fakeRoundId = 10L;
    }

    @Nested
    @DisplayName("ownerSellerValidate() 테스트")
    class ownerSellerValidate{
        @Test
        @DisplayName("예외1: id가 유효하지 않을때")
        void ownerSellerValidate1() {
            //given
            when(experienceReservationRepository.existsById(fakeReservationId)).thenReturn(false);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.ownerSellerValidate(seller, fakeReservationId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID);
        }
        @Test
        @DisplayName("예외2: 다른판매자일때")
        void ownerSellerValidate2() {
            //given
            Seller seller2 = makeTestSeller();
            when(experienceReservationRepository.existsById(fakeReservationId)).thenReturn(true);
            when(experienceReservationRepository.findSellerById(fakeReservationId)).thenReturn(Optional.ofNullable(seller2));

            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.ownerSellerValidate(seller, fakeReservationId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
        @Test
        @DisplayName("정상동작 ")
        void ownerSellerValidate3() {
            //given
            when(experienceReservationRepository.existsById(fakeReservationId)).thenReturn(true);
            when(experienceReservationRepository.findSellerById(fakeReservationId)).thenReturn(Optional.ofNullable(seller));
            //when & then
            assertDoesNotThrow(() -> experienceReservationValidator.ownerSellerValidate(seller, fakeReservationId));
        }
    }

    @Nested
    @DisplayName("ownerUserValidate() 테스트")
    class ownerUserValidate {
        @Test
        @DisplayName("예외1: 유효하지 않은 id 일 때")
        void ownerUserValidate1() {
            //given
            when(experienceReservationRepository.existsById(fakeReservationId)).thenReturn(false);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.ownerUserValidate(user, fakeReservationId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID);
        }
        @Test
        @DisplayName("예외2: 소유자가 일치하지 않을 때")
        void ownerUserValidate2() {
            //given
            User user2 = makeTestUser();
            when(experienceReservationRepository.existsById(fakeReservationId)).thenReturn(true);
            when(experienceReservationRepository.findUserById(fakeReservationId)).thenReturn(Optional.ofNullable(user2));
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.ownerUserValidate(user, fakeReservationId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
        @Test
        @DisplayName("정상동작")
        void ownerUserValidate3() {
            //given
            when(experienceReservationRepository.existsById(fakeReservationId)).thenReturn(true);
            when(experienceReservationRepository.findUserById(fakeReservationId)).thenReturn(Optional.ofNullable(user));
            //when & then
            assertDoesNotThrow(() -> experienceReservationValidator.ownerUserValidate(user, fakeReservationId));
        }
    }

    @Nested
    @DisplayName("checkCancelState() 테스트")
    class checkCancelState{
        @Test
        @DisplayName("예외1: 이미 취소된 상태")
        void checkCancelState1() {
            //given
            experienceReservation = makeTestExperienceReservation(user, experienceRound, reservationDate, ReservationStateType.CANCEL_RESERVATION);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.checkCancelState(experienceReservation)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.CANT_CANCEL_RESERVATION_STATE);
        }
        @Test
        @DisplayName("예외2: 이미 완료된 상태")
        void checkCancelState2() {
            //given
            experienceReservation = makeTestExperienceReservation(user, experienceRound, reservationDate, ReservationStateType.COMPLETE_RESERVATION);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.checkCancelState(experienceReservation)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.CANT_CANCEL_RESERVATION_STATE);
        }
        @Test
        @DisplayName("예외2: 이미 미승인 상태")
        void checkCancelState3() {
            //given
            experienceReservation = makeTestExperienceReservation(user, experienceRound, reservationDate, ReservationStateType.NOT_CONFIRM_RESERVATION);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.checkCancelState(experienceReservation)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.CANT_CANCEL_RESERVATION_STATE);
        }
        @Test
        @DisplayName("정상: 대기중,승인 상태")
        void checkCancelState() {
            //given
            ExperienceReservation experienceReservation1 = makeTestExperienceReservation(user, experienceRound, reservationDate, ReservationStateType.CONFIRM_RESERVATION);
            //when & then
            assertDoesNotThrow(() -> experienceReservationValidator.checkCancelState(experienceReservation));
            assertDoesNotThrow(() -> experienceReservationValidator.checkCancelState(experienceReservation1));
        }
    }

    @Nested
    @DisplayName("createExpReservationDtoValidate() 테스트")
    class createExpReservationDtoValidate{
        @Test
        @DisplayName("예외1: 가격이 잘못되었을 때")
        void createExpReservationDtoValidate1() {
            //given
            CreateExpReservationDto dto = makeTestCreateExpReservationDto(experienceRound, 100000);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.createExpReservationDtoValidate(experienceRound, 10, dto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.WRONG_FINAL_PRICE_ERROR);
        }
        @Test
        @DisplayName("예외2: 운영날짜가 아닐때(과거날짜라서)")
        void createExpReservationDtoValidate2() {
            //given
            CreateExpReservationDto dto = makeTestCreateExpReservationDto(experienceRound);
            ExperienceException exception = new ExperienceException(ExperienceErrorCode.WRONG_DATE_PARAMETER_BY_PAST_DATE);
            doThrow(exception).when(experienceCloseDateValidator).isOpenDateParameterValidate(experience,reservationDate);
            //when
            BaseErrorCode errorCode = assertThrows(ExperienceException.class,
                    () -> experienceReservationValidator.createExpReservationDtoValidate(experienceRound, 10, dto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ExperienceErrorCode.WRONG_DATE_PARAMETER_BY_PAST_DATE);
        }
        //TODO: then 에서 무엇을 비교할지 좀 더 고민하기
        @Test
        @DisplayName("예외2: 운영날짜가 아닐때")
        void createExpReservationDtoValidate3() {
            //given
            CreateExpReservationDto dto = makeTestCreateExpReservationDto(experienceRound);
            doThrow(ExperienceException.class).when(experienceCloseDateValidator).isOpenDateParameterValidate(experience,reservationDate);
            //when
            NongHaengException exception = assertThrows(NongHaengException.class,
                    () -> experienceReservationValidator.createExpReservationDtoValidate(experienceRound, 10, dto));
            //then
            assertThat(exception).isInstanceOf(ExperienceException.class);
        }
        @Test
        @DisplayName("예외3: 인원이 충분하지 않을 때")
        void createExpReservationDtoValidate4() {
            //given
            int remainParticipant = 0;
            CreateExpReservationDto dto = makeTestCreateExpReservationDto(experienceRound);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.createExpReservationDtoValidate(experienceRound, remainParticipant, dto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.EXCEEDED_PARTICIPANT);
        }
        @Test
        @DisplayName("정상")
        void createExpReservationDtoValidate5() {
            //given
            int remainParticipant = 10;
            CreateExpReservationDto dto = makeTestCreateExpReservationDto(experienceRound);
            doNothing().when(experienceCloseDateValidator).isOpenDateParameterValidate(experience, reservationDate);
            //when
            assertDoesNotThrow(() -> experienceReservationValidator.createExpReservationDtoValidate(experienceRound, remainParticipant, dto));
            //then
        }
    }

    @Nested
    @DisplayName("checkWaitingState() 테스트")
    class checkWaitingState{
        @Test
        @DisplayName("예외1: 대기상태가 아닐때 ")
        void checkWaitingState1() {
            //given
            ExperienceReservation cancelReservation = makeTestExperienceReservation(user, experienceRound, reservationDate, ReservationStateType.CANCEL_RESERVATION);
            ExperienceReservation confirmReservation = makeTestExperienceReservation(user, experienceRound, reservationDate, ReservationStateType.CONFIRM_RESERVATION);
            ExperienceReservation notConfirmReservation = makeTestExperienceReservation(user, experienceRound, reservationDate, ReservationStateType.NOT_CONFIRM_RESERVATION);
            ExperienceReservation completeReservation = makeTestExperienceReservation(user, experienceRound, reservationDate, ReservationStateType.COMPLETE_RESERVATION);
            //when
            BaseErrorCode errorCode1 = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.checkWaitingState(cancelReservation)).getBaseErrorCode();
            BaseErrorCode errorCode2 = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.checkWaitingState(confirmReservation)).getBaseErrorCode();
            BaseErrorCode errorCode3 = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.checkWaitingState(notConfirmReservation)).getBaseErrorCode();
            BaseErrorCode errorCode4 = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.checkWaitingState(completeReservation)).getBaseErrorCode();
            //then
            assertThat(ReservationErrorCode.NOT_WAITING_RESERVATION_STATE)
                    .isSameAs(errorCode1)
                    .isSameAs(errorCode2)
                    .isSameAs(errorCode3)
                    .isSameAs(errorCode4);
        }
        @Test
        @DisplayName("정상")
        void checkWaitingState2() {
            //when & then
            assertDoesNotThrow(()->experienceReservationValidator.checkWaitingState(experienceReservation));
        }
    }

    @Nested
    @DisplayName("checkPointValidate() 테스트")
    class checkPointValidate{
        @Test
        @DisplayName("예외1: 포인트 부족")
        void checkPointValidate1() {
            //given
            user.givePoint(1000);
            int needPoint = 2000;
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.checkPointValidate(user, needPoint)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NOT_ENOUGH_POINT_ERROR);
        }

        @Test
        @DisplayName("정상")
        void checkPointValidate2() {
            //given
            user.givePoint(2000);
            int needPoint = 1000;
            //when & then
            assertDoesNotThrow(() -> experienceReservationValidator.checkPointValidate(user, needPoint));
        }
    }

    @Nested
    @DisplayName("pageValidate() 테스트")
    class  pageValidate{
        @Test
        @DisplayName("예외1: 빈페이지")
        void pageValidate1() {
            //given
            Page<ExperienceReservation> page = new PageImpl<>(new ArrayList<>());
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.pageValidate(page)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_RESERVATION_CONTENT_AT_CURRENT_PAGE_ERROR);
        }
        @Test
        @DisplayName("정상 ")
        void pageValidate2() {
            //given
            Page<ExperienceReservation> page = new PageImpl<>(List.of(experienceReservation));
            //when & then
            assertDoesNotThrow(()->experienceReservationValidator.pageValidate(page));
        }
    }

    @Nested
    @DisplayName("idValidate() 테스트")
    class idValidate{

        @Test
        @DisplayName("예외1: 유효하지않는 id")
        void idValidate() {
            //given
            when(experienceReservationRepository.existsById(fakeReservationId)).thenReturn(false);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> experienceReservationValidator.idValidate(fakeReservationId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_EXIST_EXPERIENCE_RESERVATION_BY_ID);
        }

        @Test
        @DisplayName("정상동작")
        void idValidate2() {
            //given
            when(experienceReservationRepository.existsById(fakeReservationId)).thenReturn(true);
            //when & then
            assertDoesNotThrow(() -> experienceReservationValidator.idValidate(fakeReservationId));
        }
    }

    private CreateExpReservationDto makeTestCreateExpReservationDto(ExperienceRound experienceRound) {
        return CreateExpReservationDto.builder()
                .roundId(fakeRoundId)
                .reservationDate(reservationDate)
                .numOfParticipant(2)
                .number("010-1234-1234")
                .reservationName("test")
                .email("test@email.com")
                .finalPrice(experience.getPrice() * 2)
                .build();
    }

    private CreateExpReservationDto makeTestCreateExpReservationDto(ExperienceRound experienceRound,int finalPrice) {
        return CreateExpReservationDto.builder()
                .roundId(fakeRoundId)
                .reservationDate(reservationDate)
                .numOfParticipant(2)
                .number("010-1234-1234")
                .reservationName("test")
                .email("test@email.com")
                .finalPrice(finalPrice)
                .build();
    }


}