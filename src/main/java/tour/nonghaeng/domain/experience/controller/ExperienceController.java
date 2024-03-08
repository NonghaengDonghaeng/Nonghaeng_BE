package tour.nonghaeng.domain.experience.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.experience.dto.CreateExpDto;
import tour.nonghaeng.domain.experience.service.ExperienceService;
import tour.nonghaeng.domain.member.entity.Seller;
import tour.nonghaeng.global.auth.service.AuthService;

@RestController
@RequestMapping("/experiences")
@RequiredArgsConstructor
@Slf4j
public class ExperienceController {

    private final ExperienceService experienceService;
    private final AuthService authService;

    //관리자 API: 체험 등록하기
    @PostMapping("/seller/add")
    public ResponseEntity<String> add(Authentication authentication, @RequestBody CreateExpDto createExpDto) {

        Seller seller = authService.toSellerEntity(authentication);

        Long expId = experienceService.add(seller, createExpDto);

        return new ResponseEntity<>("체험등록 성공, 체험 id : "+expId, HttpStatus.OK);
    }
}
