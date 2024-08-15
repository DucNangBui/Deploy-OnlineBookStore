package com.ecommerce.id.vn.paymentVNpay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private long amount;
    private String bankCode;
    private String language;
    private String codeOrder;
}
