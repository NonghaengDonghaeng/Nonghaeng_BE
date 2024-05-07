package tour.nonghaeng.domain.member.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.member.exception.SellerException;
import tour.nonghaeng.domain.member.exception.error.SellerErrorCode;

@Component
@RequiredArgsConstructor
public class SellerValidator {

    private final SellerRepository sellerRepository;

    public void joinValidate(SellerJoinDto dto) {

        //TODO: 다른 검증로직 더 추가하기

        //비밀번호 체크
        if(!dto.password().equals(dto.checkPassword())){
            throw new SellerException(SellerErrorCode.PASSWORD_MISMATCH_ERROR);
        }
    }
}
