package tour.nonghaeng.global.infra.service;

import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.global.infra.dto.CreateDto;

public interface CrudService<Entity,CreateDtoType extends CreateDto> {

    //조회
    Entity findById(Long id);

    //생성
    Entity create(Member member, CreateDtoType createDto);
}
