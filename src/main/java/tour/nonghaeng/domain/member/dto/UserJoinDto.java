package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.role.Role;
import tour.nonghaeng.global.infra.enums.social.SocialType;

@JsonTypeName("user")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class UserJoinDto extends JoinDto {


        @Builder
        public UserJoinDto(AreaCode areaCode, String phoneNumber, String name, String email, String username, String password, String checkPassword) {
                super(areaCode,phoneNumber,name,email,username,password,checkPassword);

        }

        public User toEntity() {
                return User.builder()
                        .role(Role.USER)
                        .areaCode(this.getAreaCode())
                        .socialType(SocialType.ORIGIN)
                        .phoneNumber(this.getPhoneNumber())
                        .name(this.getName())
                        .email(this.getEmail())
                        .username(this.getUsername())
                        .password(this.getPassword())
                        .build();
        }
}
