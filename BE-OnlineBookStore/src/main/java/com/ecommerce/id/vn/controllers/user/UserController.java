package com.ecommerce.id.vn.controllers.user;

import com.ecommerce.id.vn.dto.UserInforDTO;
import com.ecommerce.id.vn.dto.request.UserUpdateInforDTO;
import com.ecommerce.id.vn.dto.response.ProductDTOResponse;
import com.ecommerce.id.vn.entity.User;
import com.ecommerce.id.vn.service.ProductService;
import com.ecommerce.id.vn.service.CartItemService;
import com.ecommerce.id.vn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public List<ProductDTOResponse> allBook() {
        return productService.findAll();
    }

    @GetMapping("/userInfor/{id}")
    public ResponseEntity<UserInforDTO> getUserDetailsById(@PathVariable Integer id) {
        Optional<UserInforDTO> user = userService.getUserInforById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("updateUserInfor/{id}")
    public ResponseEntity<UserInforDTO> updateUserInfor(@PathVariable Integer id,  @RequestBody UserUpdateInforDTO userUpdateInforDTO){
        Optional<UserInforDTO> userInforDTO = userService.updateUserInfor(userUpdateInforDTO, id);
        return userInforDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
