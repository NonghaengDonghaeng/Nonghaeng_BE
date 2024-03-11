package tour.nonghaeng.domain.tour.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.domain.tour.dto.CreateTourDto;
import tour.nonghaeng.domain.tour.dto.TourDetailDto;
import tour.nonghaeng.domain.tour.dto.TourSummaryDto;
import tour.nonghaeng.domain.tour.service.TourService;
import tour.nonghaeng.global.auth.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor
@Slf4j
public class TourController {

    private final TourService tourService;
    private final AuthService authService;

    //여행 리스트 조회(파라미터 page, 예시 page=0)
    @GetMapping
    public ResponseEntity<Page<TourSummaryDto>> showTourList(@PageableDefault(size=3) Pageable pageable) {

        Page<TourSummaryDto> tourSummaryDtoPage = tourService.showTourSummary(pageable);

        return new ResponseEntity<>(tourSummaryDtoPage, HttpStatus.OK);
    }

    //관리자 API: 여행 등록하기
    @PostMapping("/seller/add")
    public ResponseEntity<String> createTour(Authentication authentication,@RequestBody CreateTourDto createTourDto) {

        Seller seller = authService.toSellerEntity(authentication);
        Long tourId = tourService.create(seller, createTourDto);

        return new ResponseEntity<>("여행지 등록 성공, 여행지 id: "+tourId, HttpStatus.CREATED);
    }

    //여행 상세 조회
    @GetMapping("/{tourId}")
    public ResponseEntity<TourDetailDto> showTourDetail(@PathVariable Long tourId) {

        TourDetailDto dto = tourService.findByTourId(tourId);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


}
