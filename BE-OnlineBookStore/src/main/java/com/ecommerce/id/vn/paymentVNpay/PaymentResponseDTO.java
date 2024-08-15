package com.ecommerce.id.vn.paymentVNpay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    public String code;
    public String message;
    public String paymentUrl;
}
