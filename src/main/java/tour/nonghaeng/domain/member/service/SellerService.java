package tour.nonghaeng.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.repo.SellerRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SellerService {

    private final SellerRepository sellerRepository;

    private final PasswordEncoder passwordEncoder;


    public void join(SellerJoinDto dto) throws Exception {

        //TODO: 인증과정에서의 예외처리

        if (!dto.password().equals(dto.checkPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        Seller joinSeller = dto.toEntity();
        joinSeller.passwordEncode(passwordEncoder);
        sellerRepository.save(joinSeller);
    }
}
