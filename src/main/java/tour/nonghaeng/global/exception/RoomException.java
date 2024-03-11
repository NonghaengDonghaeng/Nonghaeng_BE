package tour.nonghaeng.global.exception;

import tour.nonghaeng.global.exception.code.RoomErrorCode;

public class RoomException extends NongHaengException{

    public static final RoomException EXCEPTION = new RoomException();

    public RoomException() {
        super(RoomErrorCode.DEFAULT_ROOM_ERROR);
    }

    public RoomException(RoomErrorCode errorCode) {
        super(errorCode);
    }

}
