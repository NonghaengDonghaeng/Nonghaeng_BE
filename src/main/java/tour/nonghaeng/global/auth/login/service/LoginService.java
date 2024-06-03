package tour.nonghaeng.global.auth.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.member.dto.TempMember;
import tour.nonghaeng.domain.member.data.repo.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TempMember tempMember = memberRepository.findTempUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return User.builder()
                .username(tempMember.getUsername())
                .password(tempMember.getPassword())
                .roles(tempMember.getRole().name())
                .build();
    }
}
