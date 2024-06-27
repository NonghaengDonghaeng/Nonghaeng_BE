package tour.nonghaeng.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.Seller;
import tour.nonghaeng.domain.member.data.repo.SellerRepository;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.presentation.exception.MemberException;
import tour.nonghaeng.domain.member.presentation.exception.error.MemberErrorCode;
import tour.nonghaeng.domain.member.service.valid.MemberValidator;
import tour.nonghaeng.global.auth.AuthValidator;
import tour.nonghaeng.global.infra.enums.cancel.CancelPolicy;
import tour.nonghaeng.global.infra.enums.role.Role;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SellerService implements MemberService<Seller, SellerJoinDto> {

    private final SellerRepository sellerRepository;

    private final MemberValidator memberValidator;
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
                        new MemberException(MemberErrorCode.NO_EXIST_MEMBER_BY_USERNAME));

    }


    @Override
    public Seller join(SellerJoinDto dto) {

        //TODO: 인증과정에서의 예외처리
        memberValidator.joinValidate(dto);

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
