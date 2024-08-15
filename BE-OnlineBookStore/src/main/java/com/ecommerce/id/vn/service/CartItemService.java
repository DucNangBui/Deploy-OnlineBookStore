package com.ecommerce.id.vn.service;

import com.ecommerce.id.vn.dto.request.CartDTO;
import com.ecommerce.id.vn.dto.request.CartItemDTO;
import com.ecommerce.id.vn.entity.CartItem;
import com.ecommerce.id.vn.entity.User;

import java.util.List;

public interface CartItemService {
    List<CartItemDTO> listCatItemByUserdemo();
    String addProductToCart(int productId, int quantity);
    String updateCartItemQuantity(int productId, int quantity);
    String removeProductFromCart(int productId);
}
