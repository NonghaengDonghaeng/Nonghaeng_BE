package tour.nonghaeng.domain.member.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.global.login.dto.TempMember;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static tour.nonghaeng.global.testEntity.user.TestUser.makeTestUser;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "/application-data.properties")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static User user;

    @BeforeEach
    void setup() {
        user = makeTestUser();
    }

    @Test
    @DisplayName("전화번호로 회원조회")
    void findByNumber() {
        //given

        User savedUser = userRepository.save(user);
        //when
        User findUser = userRepository.findByNumber(savedUser.getNumber()).get();
        //then
        assertSame(savedUser, findUser);
        assertEquals(savedUser.getName(), findUser.getName());
        assertEquals(savedUser.getId(), findUser.getId());
    }

    @Test
    @DisplayName("전화번호로 TempUser 만들기")
    void findByTempUserByNumber() {
        //given
        User savedUser = userRepository.save(user);
        //when
        TempMember findTempMember = userRepository.findByTempUserByNumber(savedUser.getNumber()).get();
        //then

        assertThat(savedUser.getNumber()).isEqualTo(findTempMember.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(findTempMember.getPassword());
        assertThat(savedUser.getRole()).isEqualTo(findTempMember.getRole());

    }
}