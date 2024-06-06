package tour.nonghaeng.domain.member.service;

import tour.nonghaeng.domain.etc.enums.cancel.CancelPolicy;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.dto.JoinDto;

public interface MemberService<Entity extends Member,JoinDtoType extends JoinDto> {


    Role getRole();

    Member findMemberByUsername(String username);

    Entity join(JoinDtoType dto);

    //포인트 관련

    void payPoint(Member user, int price);

    int payBackPoint(Member user, int price, CancelPolicy cancelPolicy);


}
