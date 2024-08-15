package com.ecommerce.id.vn.repository;

import com.ecommerce.id.vn.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByBookNameContainingOrAuthorContaining(String bookNameKeyword, String authorKeyword);

    List<Product> findByBookCategoryContaining(String categoryKeyword);

    @Override
    Page<Product> findAll(Pageable pageable);
}
