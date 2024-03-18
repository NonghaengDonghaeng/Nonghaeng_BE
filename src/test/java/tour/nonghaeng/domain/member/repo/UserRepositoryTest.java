package tour.nonghaeng.domain.member.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.global.login.dto.TempMember;

import static org.assertj.core.api.Assertions.assertThat;
import static tour.nonghaeng.global.user.TestUser.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .role(USER_ROLE)
                .areaCode(USER_AREA_CODE)
                .number(USER_NUMBER)
                .name(USER_NAME)
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .build();
    }

    @Test
    @DisplayName("전화번호로 회원조회")
    void findByNumber() {
        //given

        User savedUser = userRepository.save(user);
        //when
        User findUser = userRepository.findByNumber("010-1234-1234").get();
        //then
        Assertions.assertSame(savedUser, findUser);
        Assertions.assertEquals(savedUser.getName(), findUser.getName());
        Assertions.assertEquals(savedUser.getId(), findUser.getId());
    }

    @Test
    @DisplayName("전화번호로 TempUser 만들기")
    void findByTempUserByNumber() {
        //given
        User savedUser = userRepository.save(user);
        //when
        TempMember findTempMember = userRepository.findByTempUserByNumber("010-1234-1234").get();
        //then

        assertThat(savedUser.getNumber()).isEqualTo(findTempMember.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(findTempMember.getPassword());
        assertThat(savedUser.getRole()).isEqualTo(findTempMember.getRole());

    }
}