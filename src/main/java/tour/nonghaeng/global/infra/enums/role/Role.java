package tour.nonghaeng.global.infra.enums.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tour.nonghaeng.global.infra.exception.GlobalException;
import tour.nonghaeng.global.infra.exception.error.GlobalErrorCode;

@RequiredArgsConstructor
@Getter
public enum Role {
    GUEST_USER("ROLE_GUEST_USER","미승인사용자"),
    USER("ROLE_USER","일반사용자"),
    GUEST_SELLER("ROLE_GUEST_SELLER","미승인공급자"),
    SELLER("ROLE_SELLER","일반공급자"),
    ADMIN("ROLE_ADMIN","일반관리자"),
    ;

    private final String key;
    private final String title;

    public static Role findByKey(String key) {
        for (Role role : values()) {
            if (role.getKey().equals(key)) {
                return role;
            }
        }
        throw new GlobalException(GlobalErrorCode.NO_MATCH_ROLE_TYPE);
    }
}
