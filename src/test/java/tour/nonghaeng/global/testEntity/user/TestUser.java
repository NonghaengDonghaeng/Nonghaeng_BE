package tour.nonghaeng.global.testEntity.user;

import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.role.Role;
import tour.nonghaeng.domain.member.entity.User;

public class TestUser {

    public static final String USER_NUMBER = "010-1234-1234";
    public static final String USER_NAME = "testUser";
    public static final String USER_PASSWORD = "1234";
    public static final String USER_EMAIL = "testUser@email.com";
    public static final Role USER_ROLE = Role.USER;
    public static final AreaCode USER_AREA_CODE = AreaCode.DAEJEON;

    public static int num = -1;

    public static User makeTestUser() {
        num++;
        return User.builder()
                .role(USER_ROLE)
                .areaCode(USER_AREA_CODE)
                .number(USER_NUMBER+String.valueOf(num))
                .name(USER_NAME+String.valueOf(num))
                .password(USER_PASSWORD)
                .email(USER_EMAIL+String.valueOf(num))
                .build();
    }


}
