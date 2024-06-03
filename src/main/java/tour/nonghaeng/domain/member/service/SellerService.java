package tour.nonghaeng.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.presentation.exception.UserException;
import tour.nonghaeng.domain.member.presentation.exception.error.UserErrorCode;
import tour.nonghaeng.domain.member.data.repo.SellerRepository;
import tour.nonghaeng.domain.member.service.valid.SellerValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SellerService implements MemberService{

    private final SellerRepository sellerRepository;
    private final SellerValidator sellerValidator;

    private final PasswordEncoder passwordEncoder;




    public Seller join(SellerJoinDto dto) {

        //TODO: 인증과정에서의 예외처리
        sellerValidator.joinValidate(dto);

        Seller joinSeller = dto.toEntity();
        joinSeller.passwordEncode(passwordEncoder);

        return sellerRepository.save(joinSeller);
    }



    public int payBackPoint(Seller seller, int price) {

        seller.payBackPoint(price);

        sellerRepository.save(seller);

        return seller.getPoint();
    }

    @Override
    public Role getRole() {
        return Role.SELLER;
    }

    @Override
    public Member findMemberByUsername(String username) {
        return sellerRepository.findMemberByUsername(username)
                .orElseThrow(() ->
                        new UserException(UserErrorCode.NO_EXIST_USER_BY_NUMBER_ERROR));

    }
}
