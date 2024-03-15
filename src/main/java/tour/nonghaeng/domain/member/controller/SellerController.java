package tour.nonghaeng.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.member.dto.SellerJoinDto;
import tour.nonghaeng.domain.member.service.SellerService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SellerController {

    private final SellerService sellerService;


    @PostMapping("/seller-join")
    public ResponseEntity<String> join(@RequestBody SellerJoinDto sellerJoinDto) throws Exception {

        sellerService.join(sellerJoinDto);

        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }
}
