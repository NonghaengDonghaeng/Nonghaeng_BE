package tour.nonghaeng.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.dto.mypage.MyPageUserDto;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.service.ReservationService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MyPageService {

    private final ReservationService reservationService;

    public MyPageUserDto getUserMyPage(User user) {

        MyPageUserDto dto = MyPageUserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .number(user.getNumber())
                .point(user.getPoint())
                .reservations(reservationService.findReservationListByUser(user))
                .build();

        return dto;
    }
}
