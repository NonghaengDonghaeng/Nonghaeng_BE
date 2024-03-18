package tour.nonghaeng.global.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.member.repo.SellerRepository;
import tour.nonghaeng.global.login.dto.TempMember;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerLoginService implements UserDetailsService {

    private final SellerRepository sellerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TempMember tempMember = sellerRepository.findTempSellerByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));

        return User.builder()
                .username(tempMember.getUsername())
                .password(tempMember.getPassword())
                .roles(tempMember.getRole().name())
                .build();
    }
}
