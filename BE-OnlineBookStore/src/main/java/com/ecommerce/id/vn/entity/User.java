package com.ecommerce.id.vn.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

//    @Column(name = "state")
//    private String state;

    @Column(name = "role")
    private String role;

}
