package tour.nonghaeng.global.validation.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tour.nonghaeng.domain.member.data.User;
import tour.nonghaeng.domain.member.data.repo.UserRepository;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
import tour.nonghaeng.domain.member.presentation.exception.MemberException;
import tour.nonghaeng.domain.member.presentation.exception.error.MemberErrorCode;
import tour.nonghaeng.domain.member.service.valid.MemberValidator;
import tour.nonghaeng.global.infra.exception.error.BaseErrorCode;

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
    private MemberValidator memberValidator;


    private static User user;

    @BeforeEach
    void setUp() {
        user = makeTestUser();
    }

    @Test
    @DisplayName("예외1: 회원가입시 비밀번호 체크 실패")
    void joinValidate() {
        //given
        UserJoinDto dto = UserJoinDto.builder().name("test").email("test@email.com").phoneNumber("1234-1234")
                .password("password")
                .checkPassword("notPassword")
                .build();
        //when
        BaseErrorCode errorCode = assertThrows(MemberException.class,
                () -> memberValidator.joinValidate(dto)).getBaseErrorCode();
        //then
        assertThat(errorCode).isSameAs(MemberErrorCode.PASSWORD_MISMATCH_ERROR);
    }

    @Test
    @DisplayName("정상")
    void joinValidate2() {
        //given
        UserJoinDto dto = UserJoinDto.builder().name("test").email("test@email.com").phoneNumber("1234-1234")
                .password("password")
                .checkPassword("password")
                .build();
        //when & then
        assertDoesNotThrow(() -> memberValidator.joinValidate(dto));
    }
}