package tour.nonghaeng.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import tour.nonghaeng.domain.member.data.Guest;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.role.Role;

@JsonTypeName("guest")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)   //변수마다 @JsonProperty 사용 필요없이 모두 변환
@Getter
public class GuestJoinDto extends JoinDto{
    public GuestJoinDto(AreaCode areaCode, String phoneNumber, String name, String email, String username, String password, String checkPassword) {
        super(areaCode, phoneNumber, name, email, username, password, checkPassword);
    }

    public Guest toEntity() {
        return Guest.builder()
                .role(Role.GUEST_USER)
                .areaCode(this.getAreaCode())
                .phoneNumber(this.getPhoneNumber())
                .name(this.getName())
                .email(this.getEmail())
                .username(this.getUsername())
                .password(this.getPassword())
                .build();
    }
}
