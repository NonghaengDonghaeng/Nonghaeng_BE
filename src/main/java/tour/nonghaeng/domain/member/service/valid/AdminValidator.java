package tour.nonghaeng.domain.member.service.valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.data.repo.AdminRepository;
import tour.nonghaeng.domain.member.dto.AdminJoinDto;
import tour.nonghaeng.domain.member.presentation.exception.UserException;
import tour.nonghaeng.domain.member.presentation.exception.error.UserErrorCode;

@Component
@RequiredArgsConstructor
public class AdminValidator {

    private final AdminRepository adminRepository;

    public void joinValidate(AdminJoinDto dto) {

        //TODO: 다른 검증로직 더 추가하기

        //비밀번호 체크
        if(!dto.getPassword().equals(dto.getCheckPassword())){
            throw new UserException(UserErrorCode.PASSWORD_MISMATCH_ERROR);
        }
    }
}
