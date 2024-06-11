package tour.nonghaeng.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.data.Admin;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.data.repo.AdminRepository;
import tour.nonghaeng.domain.member.dto.AdminJoinDto;
import tour.nonghaeng.domain.member.presentation.exception.UserException;
import tour.nonghaeng.domain.member.presentation.exception.error.UserErrorCode;
import tour.nonghaeng.domain.member.service.valid.AdminValidator;
import tour.nonghaeng.global.auth.AuthValidator;
import tour.nonghaeng.global.infra.enums.cancel.CancelPolicy;
import tour.nonghaeng.global.infra.enums.role.Role;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService implements MemberService<Admin, AdminJoinDto> {

    private final AdminRepository adminRepository;

    private final AdminValidator adminValidator;
    private final AuthValidator authValidator;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public Member findMemberByUsername(String username) {
        return adminRepository.findMemberByUsername(username)
                .orElseThrow(() ->
                        new UserException(UserErrorCode.NO_EXIST_USER_BY_NUMBER_ERROR));
    }

    @Override
    public Admin join(AdminJoinDto dto) {

        adminValidator.joinValidate(dto);

        Admin joinAdmin = dto.toEntity();
        joinAdmin.passwordEncode(passwordEncoder);

        return adminRepository.save(joinAdmin);
    }


    @Override
    public void payPoint(Member member, int price) {

        Admin admin = authValidator.adminValidate(member);

        admin.payPoint(price);

        adminRepository.save(admin);
    }

    @Override
    public int payBackPoint(Member member, int price, CancelPolicy cancelPolicy) {
        Admin admin = authValidator.adminValidate(member);

        admin.payPoint(price);

        adminRepository.save(admin);

        return admin.getPoint();
    }
}
