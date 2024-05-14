package tour.nonghaeng.global.auth.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.global.exception.GlobalException;
import tour.nonghaeng.global.exception.error.GlobalErrorCode;

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
