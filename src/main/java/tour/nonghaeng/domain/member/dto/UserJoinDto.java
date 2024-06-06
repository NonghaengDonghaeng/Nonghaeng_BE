package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.role.Role;
import tour.nonghaeng.domain.etc.enums.social.SocialType;
import tour.nonghaeng.domain.member.data.User;

@JsonTypeName("user")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class UserJoinDto extends JoinDto {
        private AreaCode areaCode;
        private String phoneNumber;
        private String name;
        private String email;
        private String username;
        private String password;
        private String checkPassword;

        @Builder
        public UserJoinDto(AreaCode areaCode, String phoneNumber, String name, String email, String username, String password, String checkPassword) {
                this.areaCode = areaCode;
                this.phoneNumber = phoneNumber;
                this.name = name;
                this.email = email;
                this.username = username;
                this.password = password;
                this.checkPassword = checkPassword;
        }

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
