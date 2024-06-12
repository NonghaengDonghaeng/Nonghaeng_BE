package tour.nonghaeng.domain.notice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import tour.nonghaeng.domain.notice.data.Notice;
import tour.nonghaeng.global.infra.dto.SpecDto;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
@Setter
public class NoticeSpecDto extends SpecDto<Notice> {

    private String title;

    public NoticeSpecDto(String title) {
        this.title = title;
    }

    @Override
    public Specification<Notice> buildSpecification() {

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (title != null && !title.isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            return predicate;
        };
    }

    @Override
    public String toString() {
        return "NoticeSpecDto{" +
                "title='" + title + '\'' +
                '}';
    }
}
