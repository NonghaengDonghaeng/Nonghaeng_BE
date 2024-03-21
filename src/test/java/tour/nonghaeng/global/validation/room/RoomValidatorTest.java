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
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.room.repo.RoomRepository;
import tour.nonghaeng.domain.tour.entity.Tour;
import tour.nonghaeng.global.exception.RoomException;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.RoomErrorCode;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tour.nonghaeng.global.testEntity.room.TestRoom.makeTestRoom;
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

            //when

            //then
        }

        @Test
        @DisplayName("정상")
        void roomConditionValidate2() {
            //given

            //when

            //then
        }

    }


    @Test
    void showRoomSummaryRequestParamValidate() {
    }

    @Test
    void getRoomDetailDtoValidate() {
    }

    @Test
    void pageValidate() {
    }
}