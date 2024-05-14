package tour.nonghaeng.domain.member.service;

import tour.nonghaeng.domain.etc.role.Role;
import tour.nonghaeng.domain.member.entity.Member;

public interface MemberService {

    Role getRole();

    Member findMemberByUsername(String username);
}
