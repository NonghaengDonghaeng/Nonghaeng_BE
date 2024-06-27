package tour.nonghaeng.global.infra.enums.bank;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tour.nonghaeng.global.infra.exception.GlobalException;
import tour.nonghaeng.global.infra.exception.error.GlobalErrorCode;

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
            throw new GlobalException(GlobalErrorCode.CODE_IS_NULL_ERROR);
        }
        for (BankCode bc : BankCode.values()) {
            if (bc.getCode().equals(code)) {
                return bc;
            }
        }
        throw new GlobalException(GlobalErrorCode.NO_MATCH_BANK_CODE);

    }
}
