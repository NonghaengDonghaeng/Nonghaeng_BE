package tour.nonghaeng.global.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.global.login.dto.TempMember;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLoginService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {


        TempMember tempMember = userRepository.findByTempUserByNumber(number)
                .orElseThrow(() -> new UsernameNotFoundException("해당 전화번호가 존재하지 않습니다."));

        return User.builder()
                .username(tempMember.username())
                .password(tempMember.password())
                .roles(tempMember.role().name())
                .build();

    }
}
