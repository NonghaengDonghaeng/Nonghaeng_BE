package tour.nonghaeng.global.login.dto;

import tour.nonghaeng.domain.etc.role.Role;

public record TempMember(
        String username,
        String password,
        Role role) {
}
