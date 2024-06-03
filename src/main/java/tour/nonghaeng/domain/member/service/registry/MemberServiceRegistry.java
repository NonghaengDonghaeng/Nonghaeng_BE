package tour.nonghaeng.domain.member.service.registry;

import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.member.data.repo.MemberRepository;
import tour.nonghaeng.domain.member.service.MemberService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MemberServiceRegistry {

    private final Map<Role, MemberService> memberServiceMap;

    private final MemberRepository memberRepository;

    public MemberServiceRegistry(List<MemberService> memberServiceList, MemberRepository memberRepository) {
        this.memberServiceMap = memberServiceList.stream()
                .collect(Collectors.toMap(MemberService::getRole, Function.identity()));
        this.memberRepository = memberRepository;
    }

    public MemberService getService(Role role) {
        return memberServiceMap.get(role);
    }
}
