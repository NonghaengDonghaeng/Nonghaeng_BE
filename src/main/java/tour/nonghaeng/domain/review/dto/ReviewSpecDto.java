package tour.nonghaeng.domain.review.dto;

import jakarta.persistence.criteria.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.review.data.Review;
import tour.nonghaeng.global.infra.dto.SpecDto;

@NoArgsConstructor
@Getter
@Setter
public class ReviewSpecDto extends SpecDto<Review> {

    private String title;
    private String content;

    @Builder
    private ReviewSpecDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public Specification<Review> buildSpecification() {

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
