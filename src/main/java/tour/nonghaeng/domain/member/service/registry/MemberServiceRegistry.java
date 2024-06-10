package tour.nonghaeng.domain.member.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.member.dto.JoinDto;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
import tour.nonghaeng.domain.member.service.MemberService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MemberServiceRegistry {

    private final Map<Role, MemberService<Member, JoinDto>> memberServiceMap;


    public MemberServiceRegistry(List<MemberService<Member, JoinDto>> memberServiceList) {
        this.memberServiceMap = memberServiceList.stream()
                .collect(Collectors.toMap(MemberService::getRole, Function.identity()));
    }

    public MemberService<Member, JoinDto> getService(Role role) {
        return memberServiceMap.get(role);
    }

    public MemberService<Member,JoinDto> getServiceByDto(JoinDto joinDto) {
        return memberServiceMap.get(getType(joinDto));
    }

    private Role getType(JoinDto joinDto) {
        if (joinDto instanceof SellerJoinDto) {
            return Role.SELLER;
        } else if (joinDto instanceof UserJoinDto) {
            return Role.USER;
        }
        throw new RuntimeException();
    }
}
