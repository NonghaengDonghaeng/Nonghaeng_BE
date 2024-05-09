package tour.nonghaeng.domain.review.dto.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.review.entity.Review;

public class ReviewSpecification {

    public static Specification<Review> buildSpecification(String title,String content){

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (title != null && !title.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (content != null && !content.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + content.toLowerCase() + "%"));
            }

            return predicate;
        };

    }
}
