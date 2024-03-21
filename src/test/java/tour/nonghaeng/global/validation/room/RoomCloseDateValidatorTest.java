package tour.nonghaeng.global.validation.room;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.dto.AddRoomCloseDateDto;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.repo.RoomCloseDateRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.RoomErrorCode;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static tour.nonghaeng.global.testEntity.room.TestRoom.makeTestRoom;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;

@ExtendWith(MockitoExtension.class)
@DisplayName("RoomCloseDateValidator 테스트")
class RoomCloseDateValidatorTest {

    @Mock
    private RoomCloseDateRepository roomCloseDateRepository;

    @InjectMocks
    private RoomCloseDateValidator roomCloseDateValidator;

    private static Seller seller;
    private static Tour tour;
    private static Room room;

    @BeforeEach
    void setUp() {
        seller = makeTestSeller();
        tour = makeTestTour(seller);
        room = makeTestRoom(tour);
    }

    @Nested
    @DisplayName("addCLoseDateDtoValidate() 테스트")
    class addCLoseDateDtoValidate {

        @Test
        @DisplayName("예외1: 과거날짜를 등록")
        void addCLoseDateDtoValidate1() {
            //given
            LocalDate beforeDate = LocalDate.of(2024, 3, 1);
            AddRoomCloseDateDto dto1 = new AddRoomCloseDateDto(beforeDate);
            AddRoomCloseDateDto dto2 = new AddRoomCloseDateDto(LocalDate.of(2024, 5, 1));
            List<AddRoomCloseDateDto> dtoList = List.of(dto1, dto2);
            //when됨
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomCloseDateValidator.addCLoseDateDtoValidate(dtoList)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.PAST_ROOM_CLOSE_DATE_ADD_ERROR);
        }

        @Test
        @DisplayName("예외2: 중복된 날짜 존재")
        void addCLoseDateDtoValidate2() {
            //given
            AddRoomCloseDateDto dto1 = new AddRoomCloseDateDto(LocalDate.of(2024, 5, 1));
            AddRoomCloseDateDto dto2 = new AddRoomCloseDateDto(LocalDate.of(2024, 5, 2));
            AddRoomCloseDateDto dto3 = new AddRoomCloseDateDto(LocalDate.of(2024, 5, 1));
            List<AddRoomCloseDateDto> dtoList = List.of(dto1, dto2, dto3);
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomCloseDateValidator.addCLoseDateDtoValidate(dtoList)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.DUPLICATE_ROOM_CLOSE_DATE_ADD_ERROR);
        }

        @Test
        @DisplayName("정상 ")
        void addCLoseDateDtoValidate3() {
            AddRoomCloseDateDto dto1 = new AddRoomCloseDateDto(LocalDate.of(2024, 5, 1));
            AddRoomCloseDateDto dto2 = new AddRoomCloseDateDto(LocalDate.of(2024, 5, 2));
            List<AddRoomCloseDateDto> dtoList = List.of(dto1, dto2);
            //when & then
            assertDoesNotThrow(() -> roomCloseDateValidator.addCLoseDateDtoValidate(dtoList));
        }
    }

    @Nested
    @DisplayName("createAndSaveValidate() 테스트")
    class createAndSaveValidate{
        @Test
        @DisplayName("예외1: 이미 등록된 날짜")
        void createAndSaveValidate() {
            //given
            LocalDate registerDate = LocalDate.of(2024, 5, 5);
            AddRoomCloseDateDto dto = new AddRoomCloseDateDto(registerDate);

            Mockito.when(roomCloseDateRepository.existsByRoomAndCloseDate(room, registerDate)).thenReturn(true);
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomCloseDateValidator.createAndSaveValidate(room, dto)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.ALREADY_EXIST_ROOM_CLOSE_DATE_ADD_ERROR);
        }
        @Test
        @DisplayName("정상")
        void createAndSaveValidate2() {
            //given
            LocalDate notRegisterDate = LocalDate.of(2024, 5, 5);
            AddRoomCloseDateDto dto = new AddRoomCloseDateDto(notRegisterDate);

            Mockito.when(roomCloseDateRepository.existsByRoomAndCloseDate(room, notRegisterDate)).thenReturn(false);
            //when & then
            assertDoesNotThrow(() -> roomCloseDateValidator.createAndSaveValidate(room, dto));
        }
    }

    @Nested
    @DisplayName("isOpenDateParameterValidate() 테스트")
    class isOpenDateParameterValidate{

    }
        @Test
        @DisplayName("예외1: 오늘 이후")
        void isOpenDateParameterValidate1() {
            //given
            LocalDate beforeDate = LocalDate.of(2024, 2, 1);

            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomCloseDateValidator.isOpenDateParameterValidate(room, beforeDate)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.PAST_ROOM_CLOSE_DATE_ADD_ERROR);
        }

        @Test
        @DisplayName("예외2: 운영종료에 들어가 있음")
        void isOpenDateParameterValidate2() {
            //given
            LocalDate registerDate = LocalDate.of(2024, 5, 5);
            Mockito.when(roomCloseDateRepository.existsByRoomAndCloseDate(room, registerDate)).thenReturn(true);
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomCloseDateValidator.isOpenDateParameterValidate(room, registerDate)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.NOT_RUNNING_PERIOD_ERROR);
        }

        @Test
        @DisplayName("정상")
        void isOpenDateParameterValidate3() {
            //given
            LocalDate openDate = LocalDate.of(2024, 5, 5);
            Mockito.when(roomCloseDateRepository.existsByRoomAndCloseDate(room, openDate)).thenReturn(false);
            //when & then
            assertDoesNotThrow(()->roomCloseDateValidator.isOpenDateParameterValidate(room,openDate));

        }


}