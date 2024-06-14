package tour.nonghaeng.domain.payment.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.payment.dto.IamportResponseDto;
import tour.nonghaeng.domain.payment.service.PaymentService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;


    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<IamportResponseDto> valide(@PathVariable("paymentId")String paymentId){
        IamportResponseDto response = paymentService.paymentValid(paymentId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
