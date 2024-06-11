package tour.nonghaeng.domain.member.service.registry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tour.nonghaeng.global.infra.enums.role.Role;
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
@Slf4j
public class MemberServiceRegistry<Entity extends Member, JoinDtoType extends JoinDto> {

    private final Map<Role, MemberService<Entity,JoinDtoType>> memberServiceMap;


    public MemberServiceRegistry(List<MemberService<Entity,JoinDtoType>> memberServiceList) {
        this.memberServiceMap = memberServiceList.stream()
                .collect(Collectors.toMap(MemberService::getRole, Function.identity()));
    }

    public MemberService<Entity, JoinDtoType> getService(Role role) {
        return memberServiceMap.get(role);
    }

    public MemberService<Entity,JoinDtoType> getServiceByDto(JoinDto joinDto) {

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
