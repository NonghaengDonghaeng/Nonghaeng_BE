package tour.nonghaeng.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.member.valid.SellerValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SellerService {

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
}
