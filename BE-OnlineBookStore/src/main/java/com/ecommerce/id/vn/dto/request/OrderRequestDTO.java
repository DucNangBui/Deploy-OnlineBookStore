package com.ecommerce.id.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequestDTO {
    private int userId;
    private String codeOrder;
    private String nameConsignee;
    private String shippingAddress;
    private String phoneOrder;
    private BigDecimal totalAmount;
    private String payment;
    private String status;
    //private List<OrderDetailDTO> orderDetails;

}
