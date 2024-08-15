package com.ecommerce.id.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDTO {
    private int cartItemId;
    private int ProductId;
    private String photoProduct;
    private String productName;
    private double price;
    private int quantity;
    private double totalPrice;

    public CartItemDTO() {

    }
}
