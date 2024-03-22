package tour.nonghaeng.global.validation.room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.dto.RoomSummaryDto;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.entity.RoomCloseDate;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.RoomErrorCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tour.nonghaeng.global.testEntity.room.TestRoom.makeTestRoom;
import static tour.nonghaeng.global.testEntity.room.TestRoomCloseDate.makeTestRoomCloseDate;
import static tour.nonghaeng.global.testEntity.seller.TestSeller.makeTestSeller;
import static tour.nonghaeng.global.testEntity.tour.TestTour.makeTestTour;

@ExtendWith(MockitoExtension.class)
@DisplayName("RoomValidator 테스트 코드")
class RoomValidatorTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomValidator roomValidator;

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
    @DisplayName("ownerValidate() 테스트")
    class ownerValidate {
        @Test
        @DisplayName("예외1: 아이디가 존재하지 않을때")
        void ownerValidate1() {
            //given
            Long fakeId = 1L;
            Mockito.when(roomRepository.existsById(fakeId)).thenReturn(false);
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomValidator.ownerValidate(seller, fakeId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.NO_EXIST_ROOM_BY_ROOM_ID_ERROR);
        }
        @Test
        @DisplayName("예외2: 소유자가 아닐때")
        void ownerValidate2() {
            //given
            Long fakeId = 1L;
            Seller notSeller = makeTestSeller();
            Mockito.when(roomRepository.existsById(fakeId)).thenReturn(true);
            Mockito.when(roomRepository.findSellerByRoomId(fakeId)).thenReturn(Optional.ofNullable(seller));
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomValidator.ownerValidate(notSeller, fakeId)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }

        @Test
        @DisplayName("정상")
        void ownerValidate3() {
            //given
            Long fakeId = 1L;
            Mockito.when(roomRepository.existsById(fakeId)).thenReturn(true);
            Mockito.when(roomRepository.findSellerByRoomId(fakeId)).thenReturn(Optional.ofNullable(seller));
            //when & then
            assertDoesNotThrow(() -> roomValidator.ownerValidate(seller, fakeId));

        }
    }

    @Nested
    @DisplayName("roomConditionValidate() 테스트")
    class roomConditionValidate{

        @Test
        @DisplayName("예외1: 비어있을때")
        void roomConditionValidate1() {
            //given
            List<RoomSummaryDto> dtoList = new ArrayList<>();
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomValidator.roomConditionValidate(dtoList)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.NO_ROOM_AT_THIS_CONDITION);
        }

        @Test
        @DisplayName("정상")
        void roomConditionValidate2() {
            //given
            Room room2 = makeTestRoom(tour);
            RoomSummaryDto dto1 = RoomSummaryDto.toDto(room);
            RoomSummaryDto dto2 = RoomSummaryDto.toDto(room2);
            List<RoomSummaryDto> dtoList = List.of(dto1, dto2);
            //when & then
            assertDoesNotThrow(() -> roomValidator.roomConditionValidate(dtoList));
        }
    }

    @Nested
    @DisplayName("showRoomSummaryRequestParamValidate() 테스트")
    class showRoomSummaryRequestParamValidate{
        @Test
        @DisplayName("예외1: 방이 비어 있을 때")
        void showRoomSummaryRequestParamValidate1() {
            //given
            LocalDate date = LocalDate.of(2024, 5, 5);
            List<Room> dtoList = new ArrayList<>();
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomValidator.showRoomSummaryRequestParamValidate(dtoList, date)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.NO_ROOM_IN_THIS_TOUR);
        }
        @Test
        @DisplayName("예외2: 과거날짜요청할 때")
        void showRoomSummaryRequestParamValidate2() {
            //given
            LocalDate date = LocalDate.of(2023, 1, 1);
            Room room2 = makeTestRoom(tour);
            List<Room> dtoList = List.of(room, room2);
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomValidator.showRoomSummaryRequestParamValidate(dtoList, date)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.PAST_DATE_FOR_ROOM_LIST_REQUEST_ERROR);
        }
        @Test
        @DisplayName("예외3: 오픈날짜가 아닐때")
        void showRoomSummaryRequestParamValidate3() {
            //given
            LocalDate closeDate = LocalDate.of(2024, 5, 5);
            RoomCloseDate roomCloseDate = makeTestRoomCloseDate(room, closeDate);
            room.addCloseDate(roomCloseDate);
            List<Room> dtoList = List.of(room);
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomValidator.showRoomSummaryRequestParamValidate(dtoList, closeDate)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.CLOSE_DATE_FOR_ROOM_LIST_REQUEST_ERROR);
        }
        @Test
        @DisplayName("정상")
        void showRoomSummaryRequestParamValidate4() {
            //given
            LocalDate requestDate = LocalDate.of(2024, 5, 1);
            LocalDate closeDate = LocalDate.of(2024, 5, 5);
            RoomCloseDate roomCloseDate = makeTestRoomCloseDate(room, closeDate);
            room.addCloseDate(roomCloseDate);
            List<Room> dtoList = List.of(room);
            //when & then
            assertDoesNotThrow(() -> roomValidator.showRoomSummaryRequestParamValidate(dtoList, requestDate));
        }
    }

    @Nested
    @DisplayName("getRoomDetailDtoValidate() 테스트")
    class getRoomDetailDtoValidate{
        @Test
        @DisplayName("예외1: 과거날짜를 요청할때")
        void getRoomDetailDtoValidate1() {
            //given
            LocalDate requestDate = LocalDate.of(2023, 1, 1);

            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomValidator.getRoomDetailDtoValidate(room, requestDate)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.PAST_DATE_FOR_ROOM_LIST_REQUEST_ERROR);
        }

        @Test
        @DisplayName("예외2: 미운영날짜 요청할때")
        void getRoomDetailDtoValidate2() {
            //given
            LocalDate closeDate = LocalDate.of(2024, 5, 5);
            room.addCloseDate(RoomCloseDate.builder().closeDate(closeDate).build());
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomValidator.getRoomDetailDtoValidate(room, closeDate)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.CLOSE_DATE_FOR_ROOM_LIST_REQUEST_ERROR);
        }

        @Test
        @DisplayName("정상 ")
        void getRoomDetailDtoValidate3() {
            //given
            LocalDate closeDate = LocalDate.of(2024, 5, 5);
            LocalDate requestDate = LocalDate.of(2024, 7, 7);
            room.addCloseDate(RoomCloseDate.builder().closeDate(closeDate).build());
            //when & then
            assertDoesNotThrow(() -> roomValidator.getRoomDetailDtoValidate(room, requestDate));

        }
    }

    @Nested
    @DisplayName("pageValidate() 테스트")
    class pageValidate{
        @Test
        @DisplayName("예외1: 빈페이지일때")
        void pageValidate1() {
            //given
            Page<Room> emptyPage = new PageImpl<>(new ArrayList<>());
            //when
            BaseErrorCode errorCode = assertThrows(RoomException.class,
                    () -> roomValidator.pageValidate(emptyPage)).getBaseErrorCode();
            //then
            assertThat(errorCode).isSameAs(RoomErrorCode.NO_TOUR_ROOM_CONTENT_AT_CURRENT_PAGE_ERROR);
        }

        @Test
        @DisplayName("정상")
        void pageValidate2() {
            //given
            Page<Room> roomPage = new PageImpl<>(List.of(room));
            //when & then
            assertDoesNotThrow(() -> roomValidator.pageValidate(roomPage));
        }
    }

}