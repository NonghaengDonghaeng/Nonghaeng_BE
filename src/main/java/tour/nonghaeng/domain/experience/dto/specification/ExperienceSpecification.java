package tour.nonghaeng.domain.experience.dto.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.experience.ExperienceType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.List;

public class ExperienceSpecification {

    public static Specification<Experience> buildSpecification(String keyword, List<AreaCode> areaCodes, ExperienceType experienceType) {

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (keyword != null && !keyword.isEmpty()) {
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.like(criteriaBuilder.lower(root.get("experienceName")), "%" + keyword.toLowerCase() + "%"));
            }

            if (areaCodes != null && !areaCodes.isEmpty()) {
                Join<Experience, Tour> joinTable = root.join("tour", JoinType.INNER);
                CriteriaBuilder.In<AreaCode> areaCodeInClause = criteriaBuilder.in(joinTable.get("areaCode"));
                for (AreaCode areaCode : areaCodes) {
                    areaCodeInClause.value(areaCode);
                }
                predicate = criteriaBuilder.and(predicate, areaCodeInClause);
            }

            if (experienceType != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("experienceType"), experienceType));
            }

            return predicate;
        };
    }
}
