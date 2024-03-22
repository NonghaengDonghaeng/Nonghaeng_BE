package tour.nonghaeng.global.validation.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.global.exception.UserException;
import tour.nonghaeng.global.exception.code.BaseErrorCode;
import tour.nonghaeng.global.exception.code.UserErrorCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tour.nonghaeng.global.testEntity.user.TestUser.makeTestUser;

@ExtendWith(MockitoExtension.class)
@DisplayName("유저검증 테스트")
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    private static User user;

    @BeforeEach
    void setUp() {
        user = makeTestUser();
    }

    @Test
    @DisplayName("예외1: 회원가입시 비밀번호 체크 실패")
    void joinValidate() {
        //given
        UserJoinDto dto = UserJoinDto.builder().name("test").email("test@email.com").number("1234-1234")
                .password("password")
                .checkPassword("notPassword")
                .build();
        //when
        BaseErrorCode errorCode = assertThrows(UserException.class,
                () -> userValidator.joinValidate(dto)).getBaseErrorCode();
        //then
        assertThat(errorCode).isSameAs(UserErrorCode.PASSWORD_MISMATCH_ERROR);
    }

    @Test
    @DisplayName("정상")
    void joinValidate2() {
        //given
        UserJoinDto dto = UserJoinDto.builder().name("test").email("test@email.com").number("1234-1234")
                .password("password")
                .checkPassword("password")
                .build();
        //when & then
        assertDoesNotThrow(() -> userValidator.joinValidate(dto));
    }
}