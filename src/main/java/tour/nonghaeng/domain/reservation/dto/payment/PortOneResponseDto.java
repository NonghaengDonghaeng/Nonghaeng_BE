package tour.nonghaeng.domain.reservation.dto.payment;

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
    private String pgResponse;
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
}
