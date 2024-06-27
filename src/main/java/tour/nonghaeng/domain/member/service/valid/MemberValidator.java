package tour.nonghaeng.domain.member.service.valid;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.member.data.repo.MemberRepository;
import tour.nonghaeng.domain.member.dto.JoinDto;
import tour.nonghaeng.domain.member.presentation.exception.MemberException;
import tour.nonghaeng.domain.member.presentation.exception.error.MemberErrorCode;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void joinValidate(JoinDto joinDto) {
        //비밀번호 체크
        if(!joinDto.getPassword().equals(joinDto.getCheckPassword())){
            throw new MemberException(MemberErrorCode.PASSWORD_MISMATCH_ERROR);
        }
    }
}
