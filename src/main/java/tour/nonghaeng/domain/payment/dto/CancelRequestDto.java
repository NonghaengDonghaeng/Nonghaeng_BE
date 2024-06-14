package tour.nonghaeng.domain.payment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CancelRequestDto {
    private String reason;

    @Builder
    public CancelRequestDto(String reason) {
        this.reason = reason;
    }
}
