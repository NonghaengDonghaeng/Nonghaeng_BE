package tour.nonghaeng.global.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.repo.SellerRepository;
import tour.nonghaeng.domain.member.data.repo.UserRepository;
import tour.nonghaeng.domain.member.service.MemberService;
import tour.nonghaeng.domain.member.service.registry.MemberServiceRegistry;
import tour.nonghaeng.global.infra.enums.role.Role;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    private final MemberServiceRegistry<?,?> memberServiceRegistry;



    public Optional<? extends Member> toEntity(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Role role = findRole(authentication);
        if (role.equals(Role.USER)) {
            return userRepository.findByUsername(userDetails.getUsername());

        }
        return sellerRepository.findByUsername(userDetails.getUsername());
    }




    public Member toMemberEntity(Authentication authentication) {

        MemberService<?,?> memberService = memberServiceRegistry.getService(findRole(authentication));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return memberService.findMemberByUsername(userDetails.getUsername());
    }


    public Role findRole(Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String memberRole = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        return Role.findByKey(memberRole);
    }
}
