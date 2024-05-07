package tour.nonghaeng.domain.member.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.dto.UserJoinDto;
import tour.nonghaeng.domain.member.repo.UserRepository;
import tour.nonghaeng.domain.member.exception.UserException;
import tour.nonghaeng.domain.member.exception.error.UserErrorCode;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;


    public void joinValidate(UserJoinDto dto) {

        //TODO: 다른 검증로직 더 추가하기

        //비밀번호 체크
        if(!dto.password().equals(dto.checkPassword())){
            throw new UserException(UserErrorCode.PASSWORD_MISMATCH_ERROR);
        }
    }
}
