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

    //여행 리스트 조회(파라미터로 page)
    @GetMapping
    public ResponseEntity<Page<TourSummaryDto>> showTourList(@PageableDefault(size=3)
                                                             Pageable pageable) {
        Page<TourSummaryDto> tourSummaryDtoPage = tourService.findAll(pageable);

        return new ResponseEntity<>(tourSummaryDtoPage, HttpStatus.OK);
    }

    //여행 등록하기
    @PostMapping("/seller/add")
    public ResponseEntity<String> createTour(Authentication authentication,@RequestBody CreateTourDto createTourDto) {

        Seller seller = authService.toSellerEntity(authentication);
        tourService.create(seller, createTourDto);

        return new ResponseEntity<>("관광 등록 성공", HttpStatus.CREATED);
    }

    @GetMapping("/{tourId}")
    public ResponseEntity<TourDetailDto> showTourDetail(@PathVariable Long tourId) {

        TourDetailDto dto = tourService.findByTourId(tourId);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }





}
