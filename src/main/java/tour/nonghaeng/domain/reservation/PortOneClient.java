package tour.nonghaeng.domain.reservation;

import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tour.nonghaeng.domain.reservation.dto.payment.CancelRequestDto;
import tour.nonghaeng.domain.reservation.dto.payment.PortOneResponseDto;

@Component
public class PortOneClient {

    private final WebClient webClient;


    public PortOneClient(@Value("${iamport.baseUrl}") String baseUrl,
                         @Value("${iamport.secretApiKey}") String secretApiKey,
                         WebClient.Builder webClientBuilder) {

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
                    httpHeaders.add("Authorization", "PortOne "+secretApiKey);
                })
                .build();
    }

    public PortOneResponseDto getPaymentApi(String paymentUid) {

        return this.webClient.get()
                .uri("/payments/"+paymentUid)
                .retrieve()
                .bodyToMono(PortOneResponseDto.class)
                .block();
    }

    public void cancelPaymentByPaymentUid(String paymentUid,String reason) {

        if (reason == null) {
            reason = "금액 위변조로 인한 취소";
        }

        CancelRequestDto body = CancelRequestDto.builder().reason(reason).build();

        this.webClient.post()
                .uri("/payments/"+paymentUid+"/cancel")
                .body(Mono.just(body),CancelRequestDto.class)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(e -> new RuntimeException("Payment cancellation failed", e));
    }
}
