package com.ecommerce.id.vn.service;

import com.ecommerce.id.vn.dto.request.ProductDTO;
import com.ecommerce.id.vn.dto.response.ProductDTOResponse;
import com.ecommerce.id.vn.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Page<ProductDTOResponse> getEntities(PageRequest pageRequest);

    List<ProductDTOResponse> findAll();

    ProductDTOResponse getBookById(int id);

    Product addBook(MultipartFile file, ProductDTO productDTO) throws IOException;

    Product updateBook(ProductDTO productDTO, int id, MultipartFile file) throws IOException;

    void deleteBook(int id);

    List<ProductDTOResponse> searchBook(String bookCategoryKeyword);

    List<ProductDTOResponse> searchCategory(String keyword);
}
