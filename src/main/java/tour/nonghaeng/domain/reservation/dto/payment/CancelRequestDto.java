package tour.nonghaeng.domain.reservation.dto.payment;

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
