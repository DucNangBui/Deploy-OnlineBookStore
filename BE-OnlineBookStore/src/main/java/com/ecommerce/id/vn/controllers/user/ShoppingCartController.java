package com.ecommerce.id.vn.controllers.user;

import com.ecommerce.id.vn.dto.request.CartItemDTO;
import com.ecommerce.id.vn.repository.CartItemRepository;
import com.ecommerce.id.vn.service.CartItemService;
import com.ecommerce.id.vn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ShoppingCartController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping("/cart")
    public List<CartItemDTO> cartByUser() {
        return cartItemService.listCatItemByUserdemo();
    }

    @PostMapping("/addcart/{producId}/{quantity}")
    public String addProductToCart(@PathVariable int producId,@PathVariable int quantity){
        return cartItemService.addProductToCart(producId, quantity);
    }

    @PostMapping("/updatecart/{producId}/{quantity}")
    public String updateProductToCart(@PathVariable int producId,@PathVariable int quantity){
        return cartItemService.updateCartItemQuantity(producId, quantity);
    }

    @DeleteMapping("/deletecartItem/{producId}")
    public String removeProductFromCart(@PathVariable int producId){
        return cartItemService.removeProductFromCart(producId);
    }
}
