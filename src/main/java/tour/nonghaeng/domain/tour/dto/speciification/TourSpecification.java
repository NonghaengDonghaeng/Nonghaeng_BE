package tour.nonghaeng.domain.tour.dto.speciification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.tour.TourType;
import tour.nonghaeng.domain.tour.data.Tour;

import java.util.List;

public class TourSpecification {

    public static Specification<Tour> buildSpecification(String keyword, List<AreaCode> areaCodes, TourType tourType) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Initialize predicate as conjunction (AND)

            if (keyword != null && !keyword.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
            }

            if (areaCodes != null && !areaCodes.isEmpty()) {
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("areaCode"), areaCode));
                CriteriaBuilder.In<AreaCode> areaCodeInClause = criteriaBuilder.in(root.get("areaCode"));
                for (AreaCode areaCode : areaCodes) {
                    areaCodeInClause.value(areaCode);
                }
                predicate = criteriaBuilder.and(predicate, areaCodeInClause);
            }

            if (tourType != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("tourType"), tourType));
            }

            return predicate;
        };
    }
}
