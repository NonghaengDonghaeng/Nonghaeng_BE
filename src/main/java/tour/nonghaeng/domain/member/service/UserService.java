package tour.nonghaeng.domain.member.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.etc.enums.cancel.CancelPolicy;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
import tour.nonghaeng.domain.member.entity.Member;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.exception.UserException;
import tour.nonghaeng.domain.member.exception.error.UserErrorCode;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.domain.member.valid.UserValidator;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements MemberService{

    private final UserRepository userRepository;

    private final UserValidator userValidator;

    private final PasswordEncoder passwordEncoder;




    public User join(UserJoinDto dto){

        //TODO : 인증과정에서의 예외처리
        userValidator.joinValidate(dto);

        User joinUser = dto.toEntity();
        joinUser.passwordEncode(passwordEncoder);

        return userRepository.save(joinUser);
    }



    public int payPoint(User user, int price) {

        user.payPoint(price);

        return userRepository.save(user).getPoint();
    }



    public int payBackPoint(User user, int price, CancelPolicy cancelPolicy) {

        int payBackPoint = user.payBackPoint(price, cancelPolicy);

        userRepository.save(user);

        return payBackPoint;
    }

    @Override
    public Role getRole() {
        return Role.USER;
    }

    @Override
    public Member findMemberByUsername(String username) {
        return userRepository.findMemberByUsername(username)
                .orElseThrow(() ->
                        new UserException(UserErrorCode.NO_EXIST_USER_BY_NUMBER_ERROR));

    }
}
