package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.etc.role.Role;
import tour.nonghaeng.domain.etc.social.SocialType;

public record UserJoinDto(
        @JsonProperty("area_code")
        AreaCode areaCode,
        String number,
        String name,
        String email,
        String password,
        @JsonProperty("check_password")
        String checkPassword
) {
        public User toEntity(){
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
