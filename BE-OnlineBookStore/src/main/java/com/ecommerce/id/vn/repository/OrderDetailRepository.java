package com.ecommerce.id.vn.repository;

import com.ecommerce.id.vn.entity.Order;
import com.ecommerce.id.vn.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByOrderId(Integer id);

    void deleteAllByOrder(Order order);
}
