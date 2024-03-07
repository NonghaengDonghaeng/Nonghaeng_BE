package tour.nonghaeng.global.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.role.Role;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.global.exception.SellerException;
import tour.nonghaeng.global.exception.UserException;
import tour.nonghaeng.global.exception.code.SellerErrorCode;
import tour.nonghaeng.global.exception.code.UserErrorCode;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    public User toUserEntity(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByNumber(userDetails.getUsername())
                .orElseThrow(() ->
                        new UserException(UserErrorCode.NO_EXIST_USER_BY_NUMBER));
    }

    public Seller toSellerEntity(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return sellerRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new SellerException(SellerErrorCode.NO_EXIST_SELLER_BY_USERNAME));
    }

    public Role findRole(Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String memberRole = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        Role role = Role.findByKey(memberRole);

        return role;
    }
}
