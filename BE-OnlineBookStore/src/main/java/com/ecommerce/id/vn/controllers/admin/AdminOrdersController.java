package com.ecommerce.id.vn.controllers.admin;

import com.ecommerce.id.vn.dto.response.AdminUserDTO;
import com.ecommerce.id.vn.dto.response.ApiResponse;
import com.ecommerce.id.vn.dto.response.OrderResponseDTO;
import com.ecommerce.id.vn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrdersController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllOrder")
    public ResponseEntity<ApiResponse> getAllOrder() {
        try {
            List<OrderResponseDTO> orderResponseDTOList = orderService.getAllOrder();
            ApiResponse apiResponse = new ApiResponse("success", orderResponseDTOList, "Orders retrieved successfully");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse apiResponse = new ApiResponse("error", null, "Failed to retrieve orders: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @PutMapping("/update/statusOrder")
    public String updateStatusOrder(@RequestParam int orderId,
                                    @RequestParam String statusOrder ) {
        return orderService.updateStatusOrder(orderId, statusOrder);
    }
}
