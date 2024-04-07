package tour.nonghaeng.domain.tour.dto;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.tour.entity.Tour;

public class TourSpecification {

    public static Specification<Tour> buildSpecification(String keyword, AreaCode areaCode, String type) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Initialize predicate as conjunction (AND)

            if (keyword != null && !keyword.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
            }

            if (areaCode != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("areaCode"), areaCode));
            }

            if (type != null && !type.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("tourType"), type));
            }

            return predicate;
        };
    }
}
