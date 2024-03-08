package tour.nonghaeng.global.exception.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ErrorReason {
    private final Integer status;
    private final String code;
    private final String reason;

    @Override
    public String toString() {
        return "ErrorReason{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
