package com.ecommerce.id.vn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateinforOrderResponseDTO {
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private String phoneOrder;
    private String payment;
    private String status;
    //private List<OrderDetailDTO> orderDetails;
}
