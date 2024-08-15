package com.ecommerce.id.vn.service;

import com.ecommerce.id.vn.dto.UserDTO;
import com.ecommerce.id.vn.dto.UserInforDTO;
import com.ecommerce.id.vn.dto.request.UserUpdateInforDTO;
import com.ecommerce.id.vn.dto.response.AdminUserDTO;
import com.ecommerce.id.vn.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User registerUser(UserDTO userDTO);

    User getUserById(int id);

    Optional<UserInforDTO> getUserInforById(int id);

    Optional<UserInforDTO> updateUserInfor(UserUpdateInforDTO userUpdateInforDTO, int id);

    boolean deleteUserByUserId(int id);

    List<AdminUserDTO> getAllUser();
}
