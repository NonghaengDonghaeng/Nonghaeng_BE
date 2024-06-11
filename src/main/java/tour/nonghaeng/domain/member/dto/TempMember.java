package tour.nonghaeng.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.enums.role.Role;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TempMember {
    private String username;
    private String password;
    private Role role;

    @Builder
    public TempMember(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
