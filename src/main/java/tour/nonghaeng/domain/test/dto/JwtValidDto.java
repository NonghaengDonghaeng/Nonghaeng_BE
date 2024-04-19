package tour.nonghaeng.domain.test.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.role.Role;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JwtValidDto {
    private boolean valid = true;
    private Role role;

    @Builder
    private JwtValidDto(boolean valid, Role role) {
        this.valid = valid;
        this.role = role;
    }
}
