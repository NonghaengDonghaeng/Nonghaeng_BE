package tour.nonghaeng.domain.test.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.global.infra.enums.role.Role;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JwtValidDto {
    private String message;
    private boolean valid;
    private Role role;

    @Builder
    private JwtValidDto(String message,boolean valid, Role role) {
        this.message = message;
        this.valid = valid;
        this.role = role;
    }


}
