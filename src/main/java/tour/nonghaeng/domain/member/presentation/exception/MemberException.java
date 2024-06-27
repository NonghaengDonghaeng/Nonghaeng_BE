package tour.nonghaeng.domain.member.presentation.exception;

import tour.nonghaeng.domain.member.presentation.exception.error.MemberErrorCode;
import tour.nonghaeng.global.infra.exception.NongHaengException;

public class MemberException extends NongHaengException {

    public static final MemberException EXCEPTION = new MemberException();

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }

    public MemberException() {
        super(MemberErrorCode.DEFAULT_MEMBER_ERROR);
    }
}
