package com.ecommerce.id.vn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInforDTO {
    private String userName;
    private String email;
    private String phone;
    private String address;
}