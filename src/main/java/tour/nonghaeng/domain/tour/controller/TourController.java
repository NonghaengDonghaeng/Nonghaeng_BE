package tour.nonghaeng.domain.tour.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.auth.service.AuthService;

@RestController("/tour")
@RequiredArgsConstructor
@Slf4j
public class TourController {

    private final TourService tourService;
    private final AuthService authService;

    @PostMapping("/seller/add")
    public ResponseEntity<String> createTour(Authentication authentication, @RequestBody CreateTourDto createTourDto) {

        log.info("여행 등록");

        Seller seller = authService.toSellerEntity(authentication);
        tourService.create(seller, createTourDto);

        return new ResponseEntity<>("관광 등록 성공", HttpStatus.CREATED);
    }
}
