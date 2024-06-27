package tour.nonghaeng.domain.member.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.member.data.repo.UserRepository;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
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
public class UserService implements MemberService<User, UserJoinDto> {


    private final UserRepository userRepository;

    private final MemberValidator memberValidator;

    private final PasswordEncoder passwordEncoder;
    private final AuthValidator authValidator;



    @Override
    public Role getRole() {
        return Role.USER;
    }

    @Override
    public Member findMemberByUsername(String username) {
        return userRepository.findMemberByUsername(username)
                .orElseThrow(() ->
                        new MemberException(MemberErrorCode.NO_EXIST_MEMBER_BY_USERNAME));

    }


    @Override
    public User join(UserJoinDto dto){

        //TODO : 인증과정에서의 예외처리
        memberValidator.joinValidate(dto);

        User joinUser = dto.toEntity();
        joinUser.passwordEncode(passwordEncoder);

        return userRepository.save(joinUser);
    }


    @Override
    public void payPoint(Member member, int price) {

        User user = authValidator.userValidate(member);

        user.payPoint(price);

        userRepository.save(user);
    }


    @Override
    public int payBackPoint(Member member, int price, CancelPolicy cancelPolicy) {

        User user = authValidator.userValidate(member);

        int payBackPoint = user.payBackPoint(price, cancelPolicy);

        userRepository.save(user);

        return payBackPoint;
    }




}
