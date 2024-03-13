package tour.nonghaeng.global.validation.experience;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.experience.repo.ExperienceRepository;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.global.exception.ExperienceException;
import tour.nonghaeng.global.exception.code.ExperienceErrorCode;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExperienceValidator {

    private final ExperienceRepository experienceRepository;

    //TODO: 이 부분은 로직에 대한 혹은 DTO 에 대한 검증이 아닌 인가에 대한 검증이니까 따로 빼서 관리할지 고민중
    //TODO: 이 부분은 컨트롤러에서 서비스로 넘어가기전에 인가를 먼저 체크하기 때문에 이 부분만 controller 에서 사용됨.
    public void ownerValidate(Seller seller,Long experienceId) {

        //검증 : 체험아이디에 해당하는 체험이 있는지
        expIdValidate(experienceId);

        //검증 : 체험아이디를 통한 판매자와 API요청 판매자가 동일한지
        if (!seller.equals(experienceRepository.findSellerByExperienceId(experienceId).get())) {
            throw new ExperienceException(ExperienceErrorCode.NO_OWNER_AUTHORIZATION_ERROR);
        }
    }

    public void pageValidate(Page<Experience> tourPages) {
        if (tourPages.isEmpty()) {
            throw new ExperienceException(ExperienceErrorCode.NO_EXPERIENCE_CONTENT_AT_CURRENT_PAGE_ERROR);
        }
    }

    public void expIdValidate(Long experienceId) {
        if (!experienceRepository.existsById(experienceId)) {
            throw new ExperienceException(ExperienceErrorCode.NO_EXIST_EXPERIENCE_BY_EXPERIENCE_ID_ERROR);
        }
    }

}
