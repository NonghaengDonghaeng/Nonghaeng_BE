package tour.nonghaeng.domain.member.dto;

import lombok.Builder;
import tour.nonghaeng.domain.member.data.Admin;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.role.Role;

public class AdminJoinDto extends JoinDto{
    @Builder
    public AdminJoinDto(AreaCode areaCode, String phoneNumber, String name, String email, String username, String password, String checkPassword) {
        super(areaCode,phoneNumber,name,email,username,password,checkPassword);

    }

    public Admin toEntity() {
        return Admin.builder()
                .role(Role.ADMIN)
                .areaCode(this.getAreaCode())
                .phoneNumber(this.getPhoneNumber())
                .name(this.getName())
                .email(this.getEmail())
                .username(this.getUsername())
                .password(this.getPassword())
                .build();
    }
}
