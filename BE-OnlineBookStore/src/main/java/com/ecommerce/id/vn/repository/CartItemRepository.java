package com.ecommerce.id.vn.repository;

import com.ecommerce.id.vn.entity.CartItem;
import com.ecommerce.id.vn.entity.Product;
import com.ecommerce.id.vn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByUserId(int userId);

    CartItem findByUserIdAndProducts_Id(int userId, int productId);

    List<CartItem> findByUser(User user);

    CartItem findByUserAndProducts(User user, Product product);

    void deleteByUserId(int userId);
}
