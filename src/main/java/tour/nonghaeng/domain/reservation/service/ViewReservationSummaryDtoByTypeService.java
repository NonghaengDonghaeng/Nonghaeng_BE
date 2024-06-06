package tour.nonghaeng.domain.reservation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tour.nonghaeng.domain.etc.enums.reservation.ReservationServiceType;
import tour.nonghaeng.domain.member.data.Member;
import tour.nonghaeng.domain.reservation.dto.ReservationSummaryDto;

public interface ViewReservationSummaryDtoByTypeService {

    ReservationServiceType getType();

    Page<? extends ReservationSummaryDto> getReservationSummaryDtoPage(Member member, Pageable pageable);



    //TODO: 여기에서 registry로 type에 따라 분류하지 말고 db상에서 spec을 인자값으로 넣어서 분류하는거로 바꿔보기
}
