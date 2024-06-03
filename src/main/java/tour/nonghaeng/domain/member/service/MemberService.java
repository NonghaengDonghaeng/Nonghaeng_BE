package tour.nonghaeng.domain.member.service;

import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.member.data.Member;

public interface MemberService {

    Role getRole();

    Member findMemberByUsername(String username);
}
