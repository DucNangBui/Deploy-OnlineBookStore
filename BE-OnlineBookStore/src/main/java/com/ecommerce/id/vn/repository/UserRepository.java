package com.ecommerce.id.vn.repository;

import com.ecommerce.id.vn.dto.UserInforDTO;
import com.ecommerce.id.vn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Query("SELECT new com.ecommerce.id.vn.dto.UserInforDTO(u.username, u.email, u.phone, u.address) FROM User u WHERE u.id = :id")
    Optional<UserInforDTO> findUserInforById(@Param("id") Integer id);

}
