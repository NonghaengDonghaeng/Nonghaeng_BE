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
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.entity.RoomReservation;
import tour.nonghaeng.domain.reservation.repo.RoomReservationRepository;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.entity.RoomCloseDate;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.ReservationException;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.ReservationErrorCode;
import tour.nonghaeng.global.exception.code.RoomErrorCode;
import tour.nonghaeng.global.validation.room.RoomCloseDateValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static tour.nonghaeng.global.testEntity.reservation.TestRoomReservation.makeTestRoomReservation;
import static tour.nonghaeng.global.testEntity.room.TestRoom.makeTestRoom;
import static tour.nonghaeng.global.testEntity.room.TestRoomCloseDate.makeTestRoomCloseDate;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;
import static tour.nonghaeng.global.testEntity.user.TestUser.makeTestUser;

@ExtendWith(MockitoExtension.class)
@DisplayName("숙소예약 검증 테스트")
class RoomReservationValidatorTest {

    @Mock
    private RoomReservationRepository roomReservationRepository;

    @Mock
    private RoomCloseDateValidator roomCloseDateValidator;

    @InjectMocks
    private RoomReservationValidator roomReservationValidator;

    private static User user;
    private static Seller seller;
    private static Tour tour;
    private static Room room;
    private static RoomCloseDate roomCloseDate;
    private static List<LocalDate> reservationDates;
    private static LocalDate closeDate;
    private static RoomReservation roomReservation;
    private static Long fakeId;

    @BeforeEach
    void setUp() {
        user = makeTestUser();
        seller = makeTestSeller();
        tour = makeTestTour(seller);
        room = makeTestRoom(tour);
        closeDate = LocalDate.of(2024, 5, 5);
        reservationDates = List.of(LocalDate.of(2024, 6, 1),LocalDate.of(2024,6,2));
        roomCloseDate = makeTestRoomCloseDate(room, closeDate);
        roomReservation = makeTestRoomReservation(user, room, reservationDates,1);
        fakeId = 1L;
    }


    @Nested
    @DisplayName("ownerSellerValidate() 테스트")
    class ownerSellerValidate{

        @Test
        @DisplayName("예외1: 아이디가 유효하지 않을 때")
        void ownerSellerValidate() {
            //given
            when(roomReservationRepository.existsById(fakeId)).thenReturn(false);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.ownerSellerValidate(seller, fakeId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_EXIST_ROOM_RESERVATION_BY_ID);
        }

        @Test
        @DisplayName("예외2: 셀러가 동일하지 않을 때")
        void ownerSellerValidate2() {
            //given
            Seller seller2 = makeTestSeller();
            when(roomReservationRepository.existsById(fakeId)).thenReturn(true);
            when(roomReservationRepository.findSellerById(fakeId)).thenReturn(Optional.ofNullable(seller));
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.ownerSellerValidate(seller2, fakeId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }

        @Test
        @DisplayName("정상")
        void ownerSellerValidate3() {
            //given
            when(roomReservationRepository.existsById(fakeId)).thenReturn(true);
            when(roomReservationRepository.findSellerById(fakeId)).thenReturn(Optional.ofNullable(seller));
            //when & then
            assertDoesNotThrow(() -> roomReservationValidator.ownerSellerValidate(seller, fakeId));
        }
    }

    @Nested
    @DisplayName("ownerUserValidate() 테스트")
    class ownerUserValidate{

        @Test
        @DisplayName("예외1: 소비자 소유가 아닐때")
        void ownerUserValidate() {
            //given
            User user2 = makeTestUser();
            when(roomReservationRepository.existsById(fakeId)).thenReturn(true);
            when(roomReservationRepository.findUserById(fakeId)).thenReturn(Optional.ofNullable(user));
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.ownerUserValidate(user2, fakeId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }

        @Test
        @DisplayName("정상")
        void ownerUserValidate2() {
            //given
            when(roomReservationRepository.existsById(fakeId)).thenReturn(true);
            when(roomReservationRepository.findUserById(fakeId)).thenReturn(Optional.ofNullable(user));
            //when & then
            assertDoesNotThrow(() -> roomReservationValidator.ownerUserValidate(user, fakeId));
        }
    }

    @Nested
    @DisplayName("checkWaitingState() 테스트")
    class checkWaitingState{

        @Test
        @DisplayName("예외1: 예약대기상태가 아닐 떄")
        void checkWaitingState1() {
            //given
            RoomReservation roomReservation1 = makeTestRoomReservation(user, room, reservationDates, 1, ReservationStateType.CANCEL_RESERVATION);
            RoomReservation roomReservation2 = makeTestRoomReservation(user, room, reservationDates, 1, ReservationStateType.CONFIRM_RESERVATION);
            RoomReservation roomReservation3 = makeTestRoomReservation(user, room, reservationDates, 1, ReservationStateType.NOT_CONFIRM_RESERVATION);
            RoomReservation roomReservation4 = makeTestRoomReservation(user, room, reservationDates, 1, ReservationStateType.COMPLETE_RESERVATION);
            //when
            BaseErrorCode errorCode1 = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.checkWaitingState(roomReservation1)).getBaseErrorCode();
            BaseErrorCode errorCode2 = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.checkWaitingState(roomReservation2)).getBaseErrorCode();
            BaseErrorCode errorCode3 = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.checkWaitingState(roomReservation3)).getBaseErrorCode();
            BaseErrorCode errorCode4 = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.checkWaitingState(roomReservation4)).getBaseErrorCode();
            //then
            assertThat(ReservationErrorCode.NOT_WAITING_RESERVATION_STATE)
                    .isSameAs(errorCode1)
                    .isSameAs(errorCode2)
                    .isSameAs(errorCode3)
                    .isSameAs(errorCode4);
        }

        @Test
        @DisplayName("정상: 예약대기상태")
        void checkWaitingState2() {
            //when & then
            assertDoesNotThrow(() -> roomReservationValidator.checkWaitingState(roomReservation));
        }
    }

    @Nested
    @DisplayName("checkCancelState() 테스트")
    class checkCancelState{

        @Test
        @DisplayName("예외1: 미승인,취소,완료 상태일때 ")
        void checkCancelState() {
            //given
            RoomReservation roomReservation1 = makeTestRoomReservation(user, room, reservationDates,1, ReservationStateType.CANCEL_RESERVATION);
            RoomReservation roomReservation2 = makeTestRoomReservation(user, room, reservationDates, 1, ReservationStateType.NOT_CONFIRM_RESERVATION);
            RoomReservation roomReservation3 = makeTestRoomReservation(user, room, reservationDates, 1, ReservationStateType.COMPLETE_RESERVATION);
            //when
            BaseErrorCode errorCode1 = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.checkCancelState(roomReservation1)).getBaseErrorCode();
            BaseErrorCode errorCode2 = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.checkCancelState(roomReservation2)).getBaseErrorCode();
            BaseErrorCode errorCode3 = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.checkCancelState(roomReservation3)).getBaseErrorCode();
            //then
            assertThat(ReservationErrorCode.CANT_CANCEL_RESERVATION_STATE)
                    .isSameAs(errorCode1)
                    .isSameAs(errorCode2)
                    .isSameAs(errorCode3);
        }

        @Test
        @DisplayName("정상: 승인,대기 상태일때")
        void checkCancelState2() {
            //given
            RoomReservation roomReservation1 = makeTestRoomReservation(user, room, reservationDates, 1, ReservationStateType.CONFIRM_RESERVATION);
            //when & then
            assertDoesNotThrow(() -> roomReservationValidator.checkCancelState(roomReservation));
            assertDoesNotThrow(() -> roomReservationValidator.checkCancelState(roomReservation1));
        }
    }

    @Nested
    @DisplayName("idValidate() 테스트")
    class idValidate{

        @Test
        @DisplayName("예외1: 유효하지 않은 아이디일 때")
        void idValidate() {
            //given
            when(roomReservationRepository.existsById(fakeId)).thenReturn(false);
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.idValidate(fakeId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_EXIST_ROOM_RESERVATION_BY_ID);
        }

        @Test
        @DisplayName("정상")
        void idValidate2() {
            //given
            when(roomReservationRepository.existsById(fakeId)).thenReturn(true);
            //when & then
            assertDoesNotThrow(()->roomReservationValidator.idValidate(fakeId));
        }
    }

    @Nested
    @DisplayName("createRoomReservationDtoValidate() 테스트")
    class createRoomReservationDtoValidate{

        @Test
        @DisplayName("예외1: 숙소 최대인원보다 인원초과")
        void createRoomReservationDtoValidate1() {
            //given
            RoomReservation reservation = makeTestRoomReservation(user, room, reservationDates, 2);
            CreateRoomReservationDto dto = CreateRoomReservationDto.builder()
                    .numOfParticipant(30)
                    .numOfRoom(3)
                    .build();
            int maxCapacity = room.getMaxCapacity() * dto.getNumOfRoom();

            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.createRoomReservationDtoValidate(room, dto)).getBaseErrorCode();
            //then
            assertThat(maxCapacity).isEqualTo(12);
            assertThat(dto.getNumOfParticipant()).isGreaterThan(maxCapacity);
            assertThat(errorCode).isSameAs(ReservationErrorCode.EXCEEDED_MAX_CAPACITY);
        }

        @Test
        @DisplayName("예외2: 가격이 맞지 않을 때")
        void createRoomReservationDtoValidate2() {
            //given
            CreateRoomReservationDto dto = CreateRoomReservationDto.builder()
                    .numOfParticipant(6)
                    .numOfRoom(2)
                    .reservationDates(reservationDates)
                    .finalPrice(2000)
                    .build();

            int additionCost = room.getAdditionalCost() * (dto.getNumOfParticipant() - (room.getStandardCapacity() * dto.getNumOfRoom()));
            int correctPrice = (room.getPriceOffPeak() * dto.getNumOfRoom() + additionCost) * reservationDates.size();
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.createRoomReservationDtoValidate(room, dto)).getBaseErrorCode();
            //then
            assertThat(additionCost).isEqualTo(10000);
            assertThat(correctPrice).isEqualTo(60000);
            assertThat(dto.getFinalPrice()).isNotEqualTo(correctPrice);
            assertThat(errorCode).isSameAs(ReservationErrorCode.WRONG_FINAL_PRICE_ERROR);
        }

        @Test
        @DisplayName("예외3: 숙소의 운영날짜가 아닌 날 예약시(과거날짜) ")
        void createRoomReservationDtoValidate3() {
            //given
            LocalDate pastDate = LocalDate.of(2023, 1, 1);
            List<LocalDate> dates = List.of(pastDate, LocalDate.of(2024, 6, 1));
            CreateRoomReservationDto dto = CreateRoomReservationDto.builder()
                    .numOfParticipant(6)
                    .numOfRoom(2)
                    .reservationDates(dates)
                    .finalPrice(60000)
                    .build();
            doThrow(new RoomException(RoomErrorCode.PAST_ROOM_CLOSE_DATE_ADD_ERROR)).when(roomCloseDateValidator).isOpenDateParameterValidate(room, pastDate);
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomReservationValidator.createRoomReservationDtoValidate(room, dto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.PAST_ROOM_CLOSE_DATE_ADD_ERROR);
        }

        @Test
        @DisplayName("예외4: 숙소의 운영날짜가 아닌 날 예약시 ")
        void createRoomReservationDtoValidate31() {
            //given
            List<LocalDate> dates = List.of(closeDate, LocalDate.of(2024, 6, 1));
            CreateRoomReservationDto dto = CreateRoomReservationDto.builder()
                    .numOfParticipant(6)
                    .numOfRoom(2)
                    .reservationDates(dates)
                    .finalPrice(60000)
                    .build();
            doThrow(new RoomException(RoomErrorCode.NOT_RUNNING_PERIOD_ERROR)).when(roomCloseDateValidator).isOpenDateParameterValidate(room, closeDate);
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomReservationValidator.createRoomReservationDtoValidate(room, dto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.NOT_RUNNING_PERIOD_ERROR);
        }

        @Test
        @DisplayName("예외5: 객실 수가 부족할 때")
        void createRoomReservationDtoValidate4() {
            //given
            LocalDate reservationDate1 = LocalDate.of(2024, 6, 1);
            LocalDate reservationDate2 = LocalDate.of(2024, 6, 2);
            List<LocalDate> dates = List.of(reservationDate1, reservationDate2);
            CreateRoomReservationDto dto = CreateRoomReservationDto.builder()
                    .numOfParticipant(6)
                    .numOfRoom(2)
                    .reservationDates(dates)
                    .finalPrice(60000)
                    .build();
            //동일한 메소드에 값에 따라 stubbing 을 다르게 할때 lenient() 사용하기
            lenient().when(roomReservationRepository.countByRoomAndReservationDate(room, reservationDate1)).thenReturn(Optional.ofNullable(Integer.valueOf(4)));
            lenient().when(roomReservationRepository.countByRoomAndReservationDate(room, reservationDate2)).thenReturn(Optional.ofNullable(Integer.valueOf(1)));
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.createRoomReservationDtoValidate(room, dto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.EXCEEDED_NUM_OF_ROOM);
        }
        @Test
        @DisplayName("정상")
        void createRoomReservationDtoValidate() {
            //given
            CreateRoomReservationDto dto = CreateRoomReservationDto.builder()
                    .numOfParticipant(6)
                    .numOfRoom(2)
                    .reservationDates(reservationDates)
                    .finalPrice(60000)
                    .build();
            when(roomReservationRepository.countByRoomAndReservationDate(eq(room),any(LocalDate.class))).thenReturn(Optional.ofNullable(Integer.valueOf(1)));
            //when & then
            assertDoesNotThrow(() -> roomReservationValidator.createRoomReservationDtoValidate(room, dto));
        }
    }

    @Nested
    @DisplayName("checkPointValidate() 테스트")
    class checkPointValidate{

        @Test
        @DisplayName("예외1: 포인트 부족")
        void checkPointValidate() {
            //given
            user.givePoint(1000);
            int finalPrice = 2000;
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.checkPointValidate(user, finalPrice)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NOT_ENOUGH_POINT_ERROR);
        }

        @Test
        @DisplayName("정상 ")
        void checkPointValidate2() {
            //given
            user.givePoint(3000);
            int finalPrice = 1000;
            //when & then
            assertDoesNotThrow(() -> roomReservationValidator.checkPointValidate(user, finalPrice));
        }
    }

    @Nested
    @DisplayName("pageValidate() 테스트")
    class pageValidate{

        @Test
        @DisplayName("예외1: 빈페이지")
        void pageValidate1() {
            //given
            Page<RoomReservation> page = new PageImpl<>(new ArrayList<>());
            //when
            BaseErrorCode errorCode = assertThrows(ReservationException.class,
                    () -> roomReservationValidator.pageValidate(page)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(ReservationErrorCode.NO_RESERVATION_CONTENT_AT_CURRENT_PAGE_ERROR);
        }

        @Test
        @DisplayName("정상")
        void pageValidate2() {
            //given
            Page<RoomReservation> page = new PageImpl<>(List.of(roomReservation));
            //when & then
            assertDoesNotThrow(() -> roomReservationValidator.pageValidate(page));
        }
    }

}