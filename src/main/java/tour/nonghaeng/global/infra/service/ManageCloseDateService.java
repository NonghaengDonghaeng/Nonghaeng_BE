package tour.nonghaeng.global.infra.service;

import tour.nonghaeng.global.infra.dto.AddCloseDateDto;

import java.util.List;

public interface ManageCloseDateService<AddCloseDateDtoType extends AddCloseDateDto> {

    void addOnlyCloseDates(Long id, List<AddCloseDateDtoType> dtoList);

    void removeOnlyCloseDates(Long id, List<AddCloseDateDtoType> dtoList);

    //스케줄러로 오래된 날짜는 자동 삭제
    List<Long> findAllIds();

    void checkOldestCloseDatePastOrNot(Long roomId);
}
