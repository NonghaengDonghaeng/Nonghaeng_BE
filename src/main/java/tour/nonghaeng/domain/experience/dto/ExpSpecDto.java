package tour.nonghaeng.domain.experience.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.global.infra.enums.area.AreaCode;
import tour.nonghaeng.global.infra.enums.experience.ExperienceType;
import tour.nonghaeng.domain.experience.data.Experience;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.global.infra.dto.SpecDto;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
@Setter
public class ExpSpecDto extends SpecDto<Experience> {

    private String keyword;
    @JsonProperty("area")
    private List<AreaCode> areaCodes;
    @JsonProperty("type")
    private ExperienceType experienceType;

    @Override
    public Specification<Experience> buildSpecification() {

        String keyword = this.getKeyword();
        List<AreaCode> areaCodes = this.getAreaCodes();
        ExperienceType experienceType = this.getExperienceType();

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
