package com.ecommerce.id.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDTO {
    private long cartId;
    private List<CartItemDTO> cartItems;
    private int totalQuantity;
    private double totalPrice;

}
