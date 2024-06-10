package tour.nonghaeng.domain.room.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.room.RoomType;
import tour.nonghaeng.domain.room.data.Room;
import tour.nonghaeng.domain.tour.data.Tour;
import tour.nonghaeng.global.infra.dto.SpecDto;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
public class RoomSpecDto extends SpecDto<Room> {

    private String keyword;
    @JsonProperty("area")
    private List<AreaCode> areaCodes;
    @JsonProperty("type")
    private RoomType roomType;

    @Override
    public Specification<Room> buildSpecification() {

        String keyword = this.getKeyword();
        List<AreaCode> areaCodes = this.getAreaCodes();
        RoomType roomType = this.getRoomType();

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (keyword != null && !keyword.isEmpty()) {
                Join<Room, Tour> tourJoin = root.join("tour");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(tourJoin.get("name")), "%" + keyword.toLowerCase() + "%"));
            }

            if (areaCodes != null && !areaCodes.isEmpty()) {
                Join<Room, Tour> roomTourJoin = root.join("tour", JoinType.INNER);
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(roomTourJoin.get("areaCode"), areaCode));
                CriteriaBuilder.In<AreaCode> areaCodeInClause = criteriaBuilder.in(roomTourJoin.get("areaCode"));
                for (AreaCode areaCode : areaCodes) {
                    areaCodeInClause.value(areaCode);
                }
                predicate = criteriaBuilder.and(predicate, areaCodeInClause);
            }

            if (roomType != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("roomType"), roomType));
            }

            return predicate;
        };
    }

}
