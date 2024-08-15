package com.ecommerce.id.vn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private int orderId;
    private String codeOrder;
    private LocalDate orderDate;
    private String nameConsignee;
    private String shippingAddress;
    private String phoneOrder;
    private BigDecimal totalAmount;
    private String statusPayment;
    private String statusOrder;
    private List<OrderDetailResponseDTO> orderDetailResponseDTO;
}
