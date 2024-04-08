package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.role.Role;
import tour.nonghaeng.domain.etc.social.SocialType;
import tour.nonghaeng.domain.member.entity.User;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserJoinDto(
        AreaCode areaCode,
        String number,
        String name,
        String email,
        String password,
        String checkPassword
) {
        public User toEntity() {
                return User.builder()
                        .role(Role.USER)
                        .areaCode(this.areaCode)
                        .socialType(SocialType.ORIGIN)
                        .number(this.number)
                        .name(this.name)
                        .email(this.email)
                        .password(this.password)
                        .build();
        }

}
