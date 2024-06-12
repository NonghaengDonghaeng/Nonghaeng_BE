package tour.nonghaeng.global.infra.dto;

import org.springframework.data.jpa.domain.Specification;

public abstract class SpecDto<T> {

    public abstract Specification<T> buildSpecification();
}

// 만약 RequestParam 대신 ModelAttribute 로 파라미터를 받기 위해서는 getter,setter 가 필요하다.
