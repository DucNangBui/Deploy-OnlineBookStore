package com.ecommerce.id.vn.controllers.admin;

import com.ecommerce.id.vn.dto.request.ProductDTO;
import com.ecommerce.id.vn.dto.response.ProductDTOResponse;
import com.ecommerce.id.vn.entity.Product;
import com.ecommerce.id.vn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String test(HttpServletRequest request) {
        System.out.println("Context Path: " + request.getContextPath());
        System.out.println("Real Path: " + request.getServletContext().getRealPath("/"));
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
        return "index";
    }

    @GetMapping("/all_book")
    public List<ProductDTOResponse> allBook() {
        return productService.findAll();
    }

    @PostMapping(value = "/add_book")
    public Product addBook(@ModelAttribute ProductDTO productDTO, @RequestParam(value = "photo", required = false)  MultipartFile photo) throws IOException {
        return productService.addBook(photo, productDTO);
    }

    @PutMapping("/update_book/{id}")
    public Product updateBook(@ModelAttribute ProductDTO productDTO, @PathVariable int id, @RequestParam(value = "photo", required = false) MultipartFile photo) throws IOException {
        return productService.updateBook(productDTO, id, photo);
    }

    @DeleteMapping("/delete_book/{id}")
    public void deleteBook(@PathVariable int id) {
        productService.deleteBook(id);
    }

    @GetMapping("/searchId/{id}")
    public ProductDTOResponse searchId(@PathVariable int id) {
        return productService.getBookById(id);
    }

}
