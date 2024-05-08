package tour.nonghaeng.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tour.nonghaeng.domain.member.dto.mypage.MyPageUserDto;
import tour.nonghaeng.domain.member.entity.User;
import tour.nonghaeng.domain.reservation.service.ReservationService;
import tour.nonghaeng.domain.review.service.ReviewService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MyPageService {

    private final ReservationService reservationService;
    private final ReviewService reviewService;

    private final PageRequest defaultPageRequest = PageRequest.of(0, 4);

    public MyPageUserDto getUserMyPage(User user) {

        MyPageUserDto dto = MyPageUserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .number(user.getNumber())
                .point(user.getPoint())
                .reservationPage(reservationService.getReservationUserSummaryDtoPage(user, defaultPageRequest,"all"))
                .reviewPage(reviewService.getReviewSummaryDtoPageByUser(user,defaultPageRequest,"all"))
                .build();

        return dto;
    }
}
