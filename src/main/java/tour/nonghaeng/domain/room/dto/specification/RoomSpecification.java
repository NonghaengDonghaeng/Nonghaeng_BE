package tour.nonghaeng.domain.room.dto.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.etc.enums.area.AreaCode;
import tour.nonghaeng.domain.etc.enums.room.RoomType;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.tour.entity.Tour;

import java.util.List;

public class RoomSpecification {

    public static Specification<Room> buildRoomSpecification(String keyword, List<AreaCode> areaCodes, RoomType roomType) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(keyword != null && !keyword.isEmpty()) {
                Join<Room, Tour> tourJoin = root.join("tour");
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.like(criteriaBuilder.lower(tourJoin.get("name")),"%"+keyword.toLowerCase()+"%"));
            }

            if(areaCodes != null && !areaCodes.isEmpty()) {
                Join<Room, Tour> roomTourJoin = root.join("tour", JoinType.INNER);
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(roomTourJoin.get("areaCode"), areaCode));
                CriteriaBuilder.In<AreaCode> areaCodeInClause = criteriaBuilder.in(roomTourJoin.get("areaCode"));
                for (AreaCode areaCode : areaCodes) {
                    areaCodeInClause.value(areaCode);
                }
                predicate = criteriaBuilder.and(predicate, areaCodeInClause);
            }

            if(roomType != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("roomType"), roomType));
            }

            return predicate;
        };
    }
}
