package com.ecommerce.id.vn.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDTO {

    private int id;
    private String bookName;
    private String author;
    private double price;
    private String bookCategory;
    private String status;
    private MultipartFile photo;
    private String email;

    public ProductDTO(String bookName, String author, double price, String bookCategory, String status, MultipartFile photo, String email) {
        this.bookName = bookName;
        this.author = author;
        this.price = price;
        this.bookCategory = bookCategory;
        this.status = status;
        this.photo = photo;
        this.email = email;
    }
}
