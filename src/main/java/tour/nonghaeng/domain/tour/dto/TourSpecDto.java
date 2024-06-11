package tour.nonghaeng.domain.tour.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.tour.TourType;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.global.infra.dto.SpecDto;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class TourSpecDto extends SpecDto<Tour> {

    private String keyword;
    @JsonProperty("area")
    private List<AreaCode> areaCodes;
    @JsonProperty("type")
    private TourType tourType;

    @Override
    public Specification<Tour> buildSpecification() {

        String keyword = this.getKeyword();
        List<AreaCode> areaCodes = this.getAreaCodes();
        TourType tourType = this.getTourType();

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
