package com.ecommerce.id.vn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
        private String status;
        private Object data;
        private String message;
}
