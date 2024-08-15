package com.ecommerce.id.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateInforDTO {
    private String userName;
    private String email;
    private String phone;
    private String address;
}
