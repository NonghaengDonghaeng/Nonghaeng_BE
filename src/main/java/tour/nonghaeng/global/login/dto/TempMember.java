package tour.nonghaeng.global.login.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.role.Role;

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
