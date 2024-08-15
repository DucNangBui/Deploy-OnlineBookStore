package com.ecommerce.id.vn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTOResponse {
    private int id;
    private String bookName;
    private String author;
    private double price;
    private String bookCategory;
    private String status;
    private String image;
}
