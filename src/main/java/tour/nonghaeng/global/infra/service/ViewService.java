package tour.nonghaeng.global.infra.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.global.infra.dto.DetailDto;
import tour.nonghaeng.global.infra.dto.SpecDto;
import tour.nonghaeng.global.infra.dto.SummaryDto;

public interface ViewService<SummaryDtoType extends SummaryDto,DetailDtoType extends DetailDto,SpecDtoType extends SpecDto<?>> {

    DetailDtoType getDetailDto(Long id);

    Page<SummaryDtoType> getSummaryDtoPage(Pageable pageable,SpecDtoType searchCondition);
}
