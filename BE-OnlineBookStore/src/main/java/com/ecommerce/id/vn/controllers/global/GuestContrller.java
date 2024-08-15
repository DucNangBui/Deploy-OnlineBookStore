package com.ecommerce.id.vn.controllers.global;

import com.ecommerce.id.vn.dto.UserDTO;
import com.ecommerce.id.vn.dto.response.ProductDTOResponse;
import com.ecommerce.id.vn.entity.User;
import com.ecommerce.id.vn.service.ProductService;
import com.ecommerce.id.vn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guest")
public class GuestContrller {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/page")// để chuyển tiếp trang /entities?page=1", "/entities?page=2", vv.
    public Page<ProductDTOResponse> getEntities(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size) {
        return productService.getEntities(PageRequest.of(page, size));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.registerUser(userDTO);
            return ResponseEntity.ok(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search/{kyeword}")
    public List<ProductDTOResponse> SearchBook(@PathVariable String kyeword) {
        return productService.searchBook(kyeword);
    }

    @GetMapping("/searchId/{id}")
    public ProductDTOResponse searchId(@PathVariable int id) {
        return productService.getBookById(id);
    }

    @GetMapping("/searchCategoty/{categoryKeyword}")
    public List<ProductDTOResponse> SearchCategory(@PathVariable String categoryKeyword) {
        return productService.searchCategory(categoryKeyword);
    }
}
