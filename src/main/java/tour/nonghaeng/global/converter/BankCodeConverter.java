package tour.nonghaeng.global.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.etc.bank.BankCode;

@Component
public class BankCodeConverter implements Converter<String, BankCode> {
    @Override
    public BankCode convert(String code) {
        return BankCode.ofCode(code);
    }
}
