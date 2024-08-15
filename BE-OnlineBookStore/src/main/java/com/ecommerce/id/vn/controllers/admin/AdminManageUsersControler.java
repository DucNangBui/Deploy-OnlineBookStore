package com.ecommerce.id.vn.controllers.admin;

import com.ecommerce.id.vn.dto.response.AdminUserDTO;
import com.ecommerce.id.vn.dto.response.ApiResponse;
import com.ecommerce.id.vn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminManageUsersControler {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse> getAllUser() {
        List<AdminUserDTO> adminUserDTOList = userService.getAllUser();
        ApiResponse apiResponse = new ApiResponse("success", adminUserDTOList, "User information retrieved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId) {
        boolean isDeleted = userService.deleteUserByUserId(userId);
        if (isDeleted) {
            return ResponseEntity.ok("Xóa người dùng thành công");
        } else {
            return ResponseEntity.status(404).body("Người dùng không tồn tại hoặc có lỗi xảy ra");
        }
    }


}

