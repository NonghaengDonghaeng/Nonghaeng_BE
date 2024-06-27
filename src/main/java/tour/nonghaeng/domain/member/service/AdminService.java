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
public class AdminService implements MemberService<Admin, AdminJoinDto> {

    private final AdminRepository adminRepository;

    private final MemberValidator memberValidator;
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
                        new MemberException(MemberErrorCode.NO_EXIST_MEMBER_BY_USERNAME));
    }

    @Override
    public Admin join(AdminJoinDto dto) {

        memberValidator.joinValidate(dto);

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
