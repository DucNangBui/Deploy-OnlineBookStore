package com.ecommerce.id.vn.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateinforOrderRequestDTO {
    private int userId;
    //private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private String phoneOrder;
    private String payment;
    private String status;
    //private List<OrderDetailDTO> orderDetails;

}
