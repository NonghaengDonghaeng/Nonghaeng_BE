package tour.nonghaeng.global.infra.service;

import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.global.infra.dto.CreateDto;
import tour.nonghaeng.global.infra.dto.DetailDto;

public interface DefaultManagerService<Entity,DetailDtoType extends DetailDto,CreateDtoType extends CreateDto> {

    DetailDtoType getDetailDtoById(Long id);

    Entity findById(Long id);

    Long create(Member member, CreateDtoType createDto);
}
