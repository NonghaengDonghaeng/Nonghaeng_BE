package tour.nonghaeng.domain.room.dto.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.etc.area.AreaCode;
import tour.nonghaeng.domain.etc.room.RoomType;
import tour.nonghaeng.domain.room.entity.Room;
import tour.nonghaeng.domain.tour.entity.Tour;

public class RoomSpecification {

    public static Specification<Room> buildRoomSpecification(String keyword, AreaCode areaCode, RoomType roomType) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(keyword != null & !keyword.isEmpty()) {
                Join<Room, Tour> tourJoin = root.join("tour");
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+keyword.toLowerCase()+"%"));
            }

            if(areaCode != null) {
                Join<Room, Tour> roomTourJoin = root.join("tour", JoinType.INNER);
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(roomTourJoin.get("areaCode"), areaCode));
            }

            if(roomType != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("roomType"), roomType));
            }

            return predicate;
        };
    }
}
