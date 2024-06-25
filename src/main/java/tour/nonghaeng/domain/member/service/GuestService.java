package tour.nonghaeng.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.data.Guest;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.repo.GuestRepository;
import tour.nonghaeng.domain.member.dto.GuestJoinDto;
import tour.nonghaeng.domain.member.presentation.exception.UserException;
import tour.nonghaeng.domain.member.presentation.exception.error.UserErrorCode;
import tour.nonghaeng.global.auth.AuthValidator;
import tour.nonghaeng.global.infra.enums.cancel.CancelPolicy;
import tour.nonghaeng.global.infra.enums.role.Role;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GuestService implements MemberService<Guest, GuestJoinDto> {

    private final GuestRepository guestRepository;


    private final AuthValidator authValidator;

    private final PasswordEncoder passwordEncoder;
    @Override
    public Role getRole() {
        return Role.GUEST_USER;
    }

    @Override
    public Member findMemberByUsername(String username) {
        return guestRepository.findMemberByUsername(username)
                .orElseThrow(() ->
                        new UserException(UserErrorCode.NO_EXIST_USER_BY_NUMBER_ERROR));
    }

    @Override
    public Guest join(GuestJoinDto dto) {

        Guest joinGuest = dto.toEntity();
        joinGuest.passwordEncode(passwordEncoder);

        return guestRepository.save(joinGuest);
    }

    @Override
    public void payPoint(Member user, int price) {

    }

    @Override
    public int payBackPoint(Member user, int price, CancelPolicy cancelPolicy) {
        return 0;
    }
}
