package com.ecommerce.id.vn.dto.response;

import com.ecommerce.id.vn.entity.Order;
import com.ecommerce.id.vn.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDTO {
//    private int cartItemId;
//    private int ProductId;
    private String photoProduct;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal totalPrice;

}
