package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.etc.enums.social.SocialType;
import tour.nonghaeng.domain.member.data.User;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserJoinDto(
        AreaCode areaCode,
        String phoneNumber,
        String name,
        String email,
        String username,
        String password,
        String checkPassword
) {
        public User toEntity() {
                return User.builder()
                        .role(Role.USER)
                        .areaCode(this.areaCode)
                        .socialType(SocialType.ORIGIN)
                        .phoneNumber(this.phoneNumber)
                        .name(this.name)
                        .email(this.email)
                        .username(this.username)
                        .password(this.password)
                        .build();
        }

}
