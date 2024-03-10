package tour.nonghaeng.domain.member.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.global.validation.UserValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final UserValidator userValidator;

    private final PasswordEncoder passwordEncoder;

    public void join(UserJoinDto dto) throws Exception {

        //TODO : 인증과정에서의 예외처리
        userValidator.joinValidate(dto);

        User joinUser = dto.toEntity();
        joinUser.passwordEncode(passwordEncoder);
        userRepository.save(joinUser);

    }


}
