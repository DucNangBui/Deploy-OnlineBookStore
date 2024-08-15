package com.ecommerce.id.vn.service;

import com.ecommerce.id.vn.dto.request.ProductDTO;
import com.ecommerce.id.vn.dto.response.ProductDTOResponse;
import com.ecommerce.id.vn.entity.Product;
import com.ecommerce.id.vn.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    //Hàm chuyển đổi Product sang ProductDTOResponse
    private ProductDTOResponse productToDTOResponse(Product product) {
        String base64Image = null;
        if (product.getImage() != null) {
            base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(product.getImage());
        }

        return new ProductDTOResponse(
                product.getId(),
                product.getBookName(),
                product.getAuthor(),
                product.getPrice(),
                product.getBookCategory(),
                product.getStatus(),
                base64Image //  truyền Base64 vào DTO
        );
    }

    @Override
    public Page<ProductDTOResponse> getEntities(PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findAll(pageRequest);

        // Sử dụng productToDTOResponse trong map()
        return productPage.map(this::productToDTOResponse);
    }

    @Override
    public List<ProductDTOResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::productToDTOResponse) //  Sử dụng productToDTOResponse
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTOResponse getBookById(int id) {
        return productRepository.findById(id)
                .map(this::productToDTOResponse)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại với ID: " + id));
    }


    @Override
    public Product addBook(MultipartFile file, ProductDTO productDTO) throws IOException {

        Product product = new Product();

        product.setBookName(productDTO.getBookName());
        product.setAuthor(productDTO.getAuthor());
        product.setPrice(productDTO.getPrice());
        product.setBookCategory(productDTO.getBookCategory());
        product.setStatus(productDTO.getStatus());

        if (file != null && !file.isEmpty()) {
            product.setImage(file.getBytes());
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateBook(ProductDTO productDTO, int id, MultipartFile file) throws IOException {
        Optional<Product> optionalBook = productRepository.findById(id);

        if (optionalBook.isPresent()) {
            Product existingBook = optionalBook.get();

            // Cập nhật thông tin của cuốn sách
            existingBook.setBookName(productDTO.getBookName());
            existingBook.setAuthor(productDTO.getAuthor());
            existingBook.setPrice(productDTO.getPrice());
            existingBook.setBookCategory(productDTO.getBookCategory());
            existingBook.setStatus(productDTO.getStatus());

            // Xử lý ảnh MỚI nếu có
            if (file != null && !file.isEmpty()) {
                existingBook.setImage(file.getBytes());
            }

            return productRepository.save(existingBook);
        } else {
            throw new EntityNotFoundException("Sản phẩm không tồn tại với ID: " + id);
        }
    }

    @Override
    public void deleteBook(int id) {
        try {
            productRepository.deleteById(id);
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Lỗi khi xóa sản phẩm" + e.getMessage());
        }
    }

    @Override
    public List<ProductDTOResponse> searchBook(String keyword) {
        List<Product> productList = productRepository.findByBookNameContainingOrAuthorContaining(keyword, keyword);

        return productList.stream()
                .map(this::productToDTOResponse) // Áp dụng productToDTOResponse
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTOResponse> searchCategory(String categoryKeyword) {
        List<Product> productList = productRepository.findByBookCategoryContaining(categoryKeyword);
        return productList.stream()
                .map(this::productToDTOResponse) // Áp dụng productToDTOResponse
                .collect(Collectors.toList());
    }
}
