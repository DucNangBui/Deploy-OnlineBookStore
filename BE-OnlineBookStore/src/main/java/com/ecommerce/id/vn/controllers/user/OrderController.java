package com.ecommerce.id.vn.controllers.user;

import com.ecommerce.id.vn.dto.request.OrderRequestDTO;
import com.ecommerce.id.vn.dto.response.OrderResponseDTO;
import com.ecommerce.id.vn.paymentVNpay.PaymentRequestDTO;
import com.ecommerce.id.vn.paymentVNpay.PaymentResponseDTO;
import com.ecommerce.id.vn.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("crateOrder")
    public Optional<OrderResponseDTO> crateOder(@RequestBody OrderRequestDTO orderRequestDTO) {
        Optional<OrderResponseDTO> orderResponseDTO = orderService.crateOrder(orderRequestDTO);
        return orderResponseDTO;
    }

    @GetMapping("/viewOrder/{userId}")
    public Optional<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable int userId) {
        return orderService.viewOrdersByUserId(userId);
    }

    @PutMapping("/update/statusPayment/{codeOrder}")
    public String cratePaymentVNpay(@PathVariable String codeOrder,
                                    @RequestParam String statusPayment ) {
        return orderService.updateStatusPayment(codeOrder, statusPayment);
    }
}
