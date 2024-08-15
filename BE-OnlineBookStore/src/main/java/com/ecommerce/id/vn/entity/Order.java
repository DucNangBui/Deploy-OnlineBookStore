package com.ecommerce.id.vn.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_date")
    private LocalDate ordeDate;

    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "name_consignee")
    private String nameConsignee;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "phone_order")
    private String phoneOder;

    @Column(name = "status_payment")
    private String statusPayment;

    @Column(name = "status_order")
    private String statusOrder;

    @Column(name = "code_order")
    private String codeOrder;

}
