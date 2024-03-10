package tour.nonghaeng.domain.experience.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tour.nonghaeng.domain.experience.dto.*;
import tour.nonghaeng.domain.experience.service.ExperienceRoundService;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.global.auth.service.AuthService;
import tour.nonghaeng.global.validation.ExperienceValidator;
import tour.nonghaeng.global.validation.TourValidator;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/experiences")
@RequiredArgsConstructor
@Slf4j
public class ExperienceController {

    private final ExperienceService experienceService;
    private final ExperienceRoundService experienceRoundService;
    private final AuthService authService;

    private final ExperienceValidator experienceValidator;

    //여행 리스트 조회(파라미터 page, 예시 page=0)
    @GetMapping
    public ResponseEntity<Page<ExpSummaryDto>> showExperienceList(@PageableDefault(size = 3) Pageable pageable) {

        Page<ExpSummaryDto> pageDto = experienceService.findAll(pageable);

        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<ExpDetailDto> showExperienceDetail(@PathVariable Long experienceId) {

        ExpDetailDto dto = experienceService.findByExpId(experienceId);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //관리자 API: 체험 등록하기(첫 등록때 회차도 같이 등록가능)
    @PostMapping("/seller/add")
    public ResponseEntity<String> add(Authentication authentication, @RequestBody CreateExpDto createExpDto) {

        Seller seller = authService.toSellerEntity(authentication);

        Long expId = experienceService.add(seller, createExpDto);

        return new ResponseEntity<>("체험등록 성공, 체험 id : " + expId, HttpStatus.OK);
    }

    //관리자 API: 체험에 대한 회차 추가 등록하기
    @PostMapping("/seller/add-round/{experienceId}")
    public ResponseEntity<String> addRounds(Authentication authentication, @PathVariable Long experienceId,
                                            @RequestBody List<AddExpRoundDto> addExpRoundDtoList) {

        experienceValidator.ownerValidate(authService.toSellerEntity(authentication), experienceId);

        Long expId = experienceService.addOnlyRounds(experienceId, addExpRoundDtoList);

        int count = addExpRoundDtoList.size();

        return new ResponseEntity<>("체험(id:" + expId + ")에 회차(" + count + "개) 등록 완료.", HttpStatus.OK);

    }

    //관리자 API: 체험에 대한 오픈날짜 추가하기
    @PostMapping("/seller/add-opendate/{experienceId}")
    public ResponseEntity<String> addOpenDates(Authentication authentication, @PathVariable Long experienceId,
                                               @RequestBody List<AddExpOpenDateDto> addExpOpenDateDtos) {

        experienceValidator.ownerValidate(authService.toSellerEntity(authentication), experienceId);

        Long expId = experienceService.addOnlyOpenDates(experienceId, addExpOpenDateDtos);

        int count = addExpOpenDateDtos.size();

        return new ResponseEntity<>("체험(id:" + expId + ")에 회차(" + count + "개) 등록 완료.", HttpStatus.OK);
    }

    //관리자 API: 등록된 오픈날짜 닫기
    @PostMapping("/seller/close-opendate/{experienceId}")
    public ResponseEntity<String> closeOpenDates(Authentication authentication, @PathVariable Long experienceId,
                                                 @RequestBody List<AddExpOpenDateDto> addExpOpenDateDtos) {

        experienceValidator.ownerValidate(authService.toSellerEntity(authentication), experienceId);

        experienceService.closeOnlyOpenDates(experienceId, addExpOpenDateDtos);

        return new ResponseEntity<>("해당 오픈날짜 삭제완료,", HttpStatus.OK);
    }

    //체험 해당 날짜에 대한 회차정보 보기(파라미터 date, 예시 date=2024-03-11)
    @GetMapping("/round-info/{experienceId}")
    public ResponseEntity<ExpRoundInfoDto> getRoundInfo(@PathVariable Long experienceId,
                                                        @RequestParam("date")
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate dateParameter) {

        ExpRoundInfoDto expRoundInfo = experienceService.getExpRoundInfo(experienceId,dateParameter);

        return new ResponseEntity<>(expRoundInfo, HttpStatus.OK);
    }


    //TODO: 추가요구사항: 어떤 날에는 등록한 회차중 일부만 열고 일부는 닫고 싶다 -> 특정날짜에 따라 회차를 닫는 기능 추가
}
