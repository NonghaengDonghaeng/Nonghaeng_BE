package tour.nonghaeng.domain.room.exception;

import tour.nonghaeng.domain.room.exception.error.RoomErrorCode;
import tour.nonghaeng.global.exception.NongHaengException;

public class RoomException extends NongHaengException {

    public static final RoomException EXCEPTION = new RoomException();

    public RoomException() {
        super(RoomErrorCode.DEFAULT_ROOM_ERROR);
    }

    public RoomException(RoomErrorCode errorCode) {
        super(errorCode);
    }

}
