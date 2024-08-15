package com.ecommerce.id.vn.dto.response;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class AdminUserDTO {
    private int userId;
    private String userName;
    private String email;
    private String phone;
    private String address;
    private Optional<List<OrderResponseDTO>> orderHistory;
}
