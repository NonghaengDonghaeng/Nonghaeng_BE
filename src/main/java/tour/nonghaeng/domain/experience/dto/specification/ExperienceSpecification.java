package tour.nonghaeng.domain.experience.dto.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.experience.ExperienceType;
import tour.nonghaeng.domain.experience.entity.Experience;
import tour.nonghaeng.domain.tour.entity.Tour;

public class ExperienceSpecification {

    public static Specification<Experience> buildSpecification(String keyword, AreaCode areaCode, ExperienceType experienceType) {

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (keyword != null && !keyword.isEmpty()) {
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.like(criteriaBuilder.lower(root.get("experienceName")), "%" + keyword.toLowerCase() + "%"));
            }

            if (areaCode != null) {
                Join<Experience, Tour> joinTable = root.join("tour", JoinType.INNER);
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(joinTable.get("areaCode"), areaCode));

            }

            if (experienceType != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("experienceType"), experienceType));
            }

            return predicate;
        };
    }
}
