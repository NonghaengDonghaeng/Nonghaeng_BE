package tour.nonghaeng.domain.etc.bank;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BankCode {
    KOREA("001","한국은행"),
    SANUP("002","산업은행"),
    NONGHUB("011","농협은행")
    ;

    @JsonValue
    private final String code;
    private final String bankName;

    public static BankCode ofCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException();
        }
        for (BankCode bc : BankCode.values()) {
            if (bc.getCode().equals(code)) {
                return bc;
            }
        }
        throw new IllegalArgumentException("일치하는 은행코드가 없습니다.");
    }
}
