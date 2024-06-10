package tour.nonghaeng.global.infra.dto;

import org.springframework.data.jpa.domain.Specification;

public abstract class SpecDto<T> {

    public abstract Specification<T> buildSpecification();
}
