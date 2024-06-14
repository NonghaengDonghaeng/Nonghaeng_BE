package tour.nonghaeng.domain.payment.presentation;

import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.payment.dto.PaymentCallbackRequestDto;
import tour.nonghaeng.domain.payment.service.PaymentService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;


    @ResponseBody
    @PostMapping("/payment")
    public ResponseEntity<IamportResponse<com.siot.IamportRestClient.response.Payment>> validationPayment(@RequestBody PaymentCallbackRequestDto request) {
        IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = paymentService.paymentByCallback(request);

        log.info("결제 응답={}", iamportResponse.getResponse().toString());

        return new ResponseEntity<>(iamportResponse, HttpStatus.OK);
    }



}
