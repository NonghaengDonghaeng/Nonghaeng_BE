package tour.nonghaeng.domain.reservation.dto.payment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PortOneResponseDto {
    private String status;
    private String id;
    private String transactionId;
    private String merchantId;
    private String storeId;
    private Method method;
    private Channel channel;
    private String version;
    private LocalDateTime requestedAt;
    private LocalDateTime updatedAt;
    private LocalDateTime statusChangedAt;
    private String orderName;
    private Amount amount;
    private String currency;
    private Customer customer;
    private boolean isCulturalExpense;
    private LocalDateTime paidAt;
    private String pgTxId;
    @JsonDeserialize(using = PgResponseDeserializer.class)
    private PgResponse pgResponse;
    private String receiptUrl;

    // Nested classes
    @Data
    public static class Method {
        private String type;
        private String provider;
        private EasyPayMethod easyPayMethod;

        @Data
        public static class EasyPayMethod {
            private String type;
        }
    }

    @Data
    public static class Channel {
        private String type;
        private String id;
        private String key;
        private String name;
        private String pgProvider;
        private String pgMerchantId;
    }

    @Data
    public static class Amount {
        private int total;
        private int taxFree;
        private int vat;
        private int supply;
        private int discount;
        private int paid;
        private int cancelled;
        private int cancelledTaxFree;
    }

    @Data
    public static class Customer {
        private String id;
        private String name;
        private String email;
        private String phoneNumber;
    }

    @Data
    public static class PgResponse {
        private String CARD_Quota;
        private String CARD_ClEvent;
        private String CARD_CorpFlag;
        private String buyerTel;
        private String parentEmail;
        private String applDate;
        private String buyerEmail;
        private String OrgPrice;
        private String p_Sub;
        private String resultCode;
        private String mid;
        private String CARD_UsePoint;
        private String CARD_Num;
        private String authSignature;
        private String tid;
        private String EventCode;
        private String goodName;
        private String TotPrice;
        private String payMethod;
        private String CARD_MemberNum;
        private String MOID;
        private String CARD_Point;
        private String currency;
        private String CARD_PurchaseCode;
        private String CARD_PrtcCode;
        private String applTime;
        private String goodsName;
        private String CARD_CheckFlag;
        private String FlgNotiSendChk;
        private String CARD_Code;
        private String CARD_BankCode;
        private String CARD_TerminalNum;
        private String P_FN_NM;
        private String buyerName;
        private String p_SubCnt;
        private String applNum;
        private String resultMsg;
        private String CARD_Interest;
        private String CARD_SrcCode;
        private String CARD_ApplPrice;
        private String CARD_GWCode;
        private String custEmail;
        private String CARD_Expire;
        private String CARD_PurchaseName;
        private String CARD_PRTC_CODE;
        private String payDevice;
    }

}
