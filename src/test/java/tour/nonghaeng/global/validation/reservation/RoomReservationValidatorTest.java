package tour.nonghaeng.global.validation.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;
import tour.nonghaeng.domain.reservation.data.RoomReservation;
import tour.nonghaeng.domain.reservation.data.repo.RoomReservationRepository;
import tour.nonghaeng.domain.reservation.service.valid.RoomReservationValidator;
import tour.nonghaeng.domain.room.data.Room;
import tour.nonghaeng.domain.room.data.RoomCloseDate;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.domain.reservation.presentation.exception.ReservationException;
import tour.nonghaeng.domain.room.presentation.exception.RoomException;
import tour.nonghaeng.global.infra.exception.error.BaseErrorCode;
import tour.nonghaeng.domain.reservation.presentation.exception.error.ReservationErrorCode;
import tour.nonghaeng.domain.room.presentation.exception.error.RoomErrorCode;
import tour.nonghaeng.domain.room.service.valid.RoomCloseDateValidator;

import java.time.LocalDate;
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


}