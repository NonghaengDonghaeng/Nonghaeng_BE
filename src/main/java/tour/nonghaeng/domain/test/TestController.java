package tour.nonghaeng.domain.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.global.exception.CustomOneException;

@RestController("/test")
@Slf4j
public class TestController {

    @PostMapping("/test-exception")
    public ResponseEntity<String> test(@RequestParam String number) {

        if (number.equals("1")) {
            throw CustomOneException.EXCEPTION;
        }

        return new ResponseEntity<>("test 성공", HttpStatus.OK);
    }
}
