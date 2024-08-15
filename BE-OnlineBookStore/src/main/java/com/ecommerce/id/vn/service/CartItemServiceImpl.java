package com.ecommerce.id.vn.service;

import com.ecommerce.id.vn.config.CustomUserDetails;
import com.ecommerce.id.vn.dto.request.CartItemDTO;
import com.ecommerce.id.vn.entity.CartItem;
import com.ecommerce.id.vn.entity.Product;
import com.ecommerce.id.vn.entity.User;
import com.ecommerce.id.vn.repository.CartItemRepository;
import com.ecommerce.id.vn.repository.ProductRepository;
import com.ecommerce.id.vn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    //Hàm chuyển đổi
    private String convertImage(byte[] imageProduc) {
        String base64Image = null;

        if (imageProduc != null) {
            base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageProduc);

        }
        return base64Image;
    }

    @Override
    public List<CartItemDTO> listCatItemByUserdemo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int idUserSS = userDetails.getId();

        List<CartItem> listCartItemByUserId = cartItemRepository.findByUserId(idUserSS);

        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        for (CartItem cartItem : listCartItemByUserId) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setCartItemId(cartItem.getId());

            String base64Image = convertImage(cartItem.getProducts().getImage());
            cartItemDTO.setPhotoProduct(base64Image);

            cartItemDTO.setProductId(cartItem.getProducts().getId());
            cartItemDTO.setProductName(cartItem.getProducts().getBookName());
            cartItemDTO.setPrice(cartItem.getProducts().getPrice());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setTotalPrice(cartItemDTO.getPrice() * cartItemDTO.getQuantity());
            cartItemDTOs.add(cartItemDTO);
        }
        return cartItemDTOs;
    }

    @Override
    public String addProductToCart(int productId, int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getId();

        // Lấy đối tượng User từ UserRepository
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return "Người dùng không tồn tại.";
        }
        User user = optionalUser.get();

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng của người dùng hay không
        CartItem cartItem = cartItemRepository.findByUserIdAndProducts_Id(userId, productId);

        if (cartItem != null) {
            // Nếu sản phẩm đã tồn tại, cập nhật số lượng sản phẩm
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            // Lưu thay đổi vào cơ sở dữ liệu
            cartItemRepository.save(cartItem);
            return "Số lượng sản phẩm trong giỏ hàng đã được cập nhật.";
        } else {
            // Nếu sản phẩm chưa tồn tại, tạo mới CartItem và thêm vào giỏ hàng
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                CartItem newCartItem = new CartItem();
                newCartItem.setUser(user);
                newCartItem.setProducts(product);
                newCartItem.setQuantity(quantity);

                // Lưu CartItem mới vào cơ sở dữ liệu
                cartItemRepository.save(newCartItem);
                return "Thêm sản phẩm vào giỏ hàng thành công.";
            } else {
                // Sản phẩm không tồn tại
                return "Sản phẩm không tồn tại.";
            }
        }
    }

    @Override
    public String updateCartItemQuantity(int productId, int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getId();

        // Kiểm tra xem sản phẩm có trong giỏ hàng của người dùng hay không
        CartItem cartItem = cartItemRepository.findByUserIdAndProducts_Id(userId, productId);

        if (cartItem != null) {
            // Cập nhật số lượng sản phẩm trong giỏ hàng
            cartItem.setQuantity(quantity);
            // Lưu thay đổi vào cơ sở dữ liệu
            cartItemRepository.save(cartItem);
            return "Số lượng sản phẩm trong giỏ hàng đã được cập nhật.";
        } else {
            // Nếu sản phẩm không tồn tại trong giỏ hàng
            return "Sản phẩm không tồn tại trong giỏ hàng.";
        }
    }

    @Override
    public String removeProductFromCart(int productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getId();

        // Kiểm tra xem sản phẩm có trong giỏ hàng của người dùng hay không
        CartItem cartItem = cartItemRepository.findByUserIdAndProducts_Id(userId, productId);

        if (cartItem != null) {
            // Xóa sản phẩm khỏi giỏ hàng
            cartItemRepository.delete(cartItem);
            return "Sản phẩm đã được xóa khỏi giỏ hàng.";
        } else {
            // Nếu sản phẩm không tồn tại trong giỏ hàng
            return "Sản phẩm không tồn tại trong giỏ hàng.";
        }
    }
}
