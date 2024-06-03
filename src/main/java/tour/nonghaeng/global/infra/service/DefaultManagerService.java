package tour.nonghaeng.global.infra.service;

import tour.nonghaeng.global.infra.dto.DetailDto;

public interface DefaultManagerService<Entity,T extends DetailDto> {
    T getDetailDtoById(Long id);
    Entity getEntityById(Long id);
}
