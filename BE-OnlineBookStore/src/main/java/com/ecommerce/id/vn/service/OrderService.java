package com.ecommerce.id.vn.service;


import com.ecommerce.id.vn.dto.request.OrderRequestDTO;
import com.ecommerce.id.vn.dto.response.OrderResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderResponseDTO> crateOrder(OrderRequestDTO orderDTO);

    @Transactional
    Optional<List<OrderResponseDTO>> viewOrdersByUserId(Integer userId);

    @Transactional
    String updateStatusPayment(String codeOrder, String statusPayment);

    @Transactional
    String updateStatusOrder(int orderId, String statusOrder);

    List<OrderResponseDTO> getAllOrder();
}
