package tour.nonghaeng.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.global.infra.enums.cancel.CancelPolicy;
import tour.nonghaeng.global.infra.enums.role.Role;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.repo.SellerRepository;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.presentation.exception.UserException;
import tour.nonghaeng.domain.member.presentation.exception.error.UserErrorCode;
import tour.nonghaeng.domain.member.service.valid.SellerValidator;
import tour.nonghaeng.global.auth.AuthValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SellerService implements MemberService<Seller, SellerJoinDto> {

    private final SellerRepository sellerRepository;

    private final SellerValidator sellerValidator;
    private final AuthValidator authValidator;

    private final PasswordEncoder passwordEncoder;


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


    @Override
    public Seller join(SellerJoinDto dto) {

        //TODO: 인증과정에서의 예외처리
        sellerValidator.joinValidate(dto);

        Seller joinSeller = dto.toEntity();
        joinSeller.passwordEncode(passwordEncoder);

        return sellerRepository.save(joinSeller);
    }

    @Override
    public void payPoint(Member member, int price) {
        Seller seller = authValidator.sellerValidate(member);

        seller.payPoint(price);

        sellerRepository.save(seller);
    }

    @Override
    public int payBackPoint(Member member, int price, CancelPolicy cancelPolicy) {

        Seller seller = authValidator.sellerValidate(member);

        seller.payBackPoint(price);

        sellerRepository.save(seller);

        return seller.getPoint();
    }

}
