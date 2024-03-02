package tour.nonghaeng.global.login.dto;

import tour.nonghaeng.domain.member.enums.Role;

public record TempMember(
        String username,
        String password,
        Role role) {
}
