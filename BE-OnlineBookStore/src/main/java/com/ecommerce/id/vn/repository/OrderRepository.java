package com.ecommerce.id.vn.repository;

import com.ecommerce.id.vn.entity.Order;
import com.ecommerce.id.vn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
    Optional<Order> findByCodeOrder(String codeOrder);
}
