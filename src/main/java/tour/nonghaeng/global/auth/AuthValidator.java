package tour.nonghaeng.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.global.infra.exception.GlobalException;
import tour.nonghaeng.global.infra.exception.error.GlobalErrorCode;

@Component
@RequiredArgsConstructor
public class AuthValidator {

    public Seller sellerValidate(Member seller) {

        if (seller instanceof Seller) {

            return (Seller) seller;
        }
        throw new GlobalException(GlobalErrorCode.MEMBER_DOWN_CASTING_ERROR);
    }

    public User userValidate(Member member) {

        if(member instanceof User) {

            return (User) member;
        }
        throw new GlobalException(GlobalErrorCode.MEMBER_DOWN_CASTING_ERROR);
    }
}
