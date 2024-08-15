package com.ecommerce.id.vn.service;

import com.ecommerce.id.vn.dto.UserDTO;
import com.ecommerce.id.vn.dto.UserInforDTO;
import com.ecommerce.id.vn.dto.request.UserUpdateInforDTO;
import com.ecommerce.id.vn.dto.response.AdminUserDTO;
import com.ecommerce.id.vn.dto.response.OrderResponseDTO;
import com.ecommerce.id.vn.entity.Order;
import com.ecommerce.id.vn.entity.User;
import com.ecommerce.id.vn.repository.CartItemRepository;
import com.ecommerce.id.vn.repository.OrderDetailRepository;
import com.ecommerce.id.vn.repository.OrderRepository;
import com.ecommerce.id.vn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public Optional<UserInforDTO> getUserInforById(int id) {
        return userRepository.findUserInforById(id);
    }

    @Override
    public Optional<UserInforDTO> updateUserInfor(UserUpdateInforDTO userUpdateInforDTO, int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userUpdateInforDTO.getUserName());
            user.setEmail(userUpdateInforDTO.getEmail());
            user.setPhone(userUpdateInforDTO.getPhone());
            user.setAddress(userUpdateInforDTO.getAddress());
            userRepository.save(user);
            UserInforDTO userInforDTO = new UserInforDTO(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getAddress()
            );
            return Optional.of(userInforDTO);
        }
        return Optional.empty();
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUserName());
        // Mã hóa mật khẩu trước khi gán
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());

        // Xác thực dữ liệu người dùng
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }
        // Thêm vai trò mặc định (tùy chọn)
        user.setRole("ROLE_USER");
        // Lưu người dùng
        return userRepository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteUserByUserId(int userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            User user = optionalUser.get();

            if ("ROLE_USER".equals(user.getRole())) {
                // 1. Xóa tất cả các mục giỏ hàng (cart_item) liên quan đến User
                cartItemRepository.deleteByUserId(userId);

                // 2. Tìm tất cả Order liên quan đến User
                List<Order> orders = orderRepository.findByUserId(userId);

                // 3. Xóa tất cả OrderDetail liên quan đến các Order vừa tìm được
                for (Order order : orders) {
                    orderDetailRepository.deleteAllByOrder(order);
                }

                // 4. Xóa Order
                orderRepository.deleteAll(orders);

                // 5. Cuối cùng, xóa User
                userRepository.deleteById(userId);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<AdminUserDTO> getAllUser() {
        try {
            List<User> users = userRepository.findAll();
            List<AdminUserDTO> adminUserDTOList = new ArrayList<>();
            for (User user : users) {
                AdminUserDTO adminUserDTO = new AdminUserDTO();
                adminUserDTO.setUserId(user.getId());
                adminUserDTO.setUserName(user.getUsername());
                adminUserDTO.setEmail(user.getEmail());
                adminUserDTO.setAddress(user.getAddress());

                Optional<List<OrderResponseDTO>> orderResponseDTOList = orderService.viewOrdersByUserId(user.getId());
                adminUserDTO.setOrderHistory(orderResponseDTOList);
                adminUserDTOList.add(adminUserDTO);
            }
            return adminUserDTOList;

        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy xuất dữ liệu từ database", e);
        }
    }
}
