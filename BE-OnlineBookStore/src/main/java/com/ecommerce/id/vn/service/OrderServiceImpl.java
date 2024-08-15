package com.ecommerce.id.vn.service;

import com.ecommerce.id.vn.dto.request.OrderRequestDTO;
import com.ecommerce.id.vn.dto.response.OrderDetailResponseDTO;
import com.ecommerce.id.vn.dto.response.OrderResponseDTO;
import com.ecommerce.id.vn.dto.response.ProductDTOResponse;
import com.ecommerce.id.vn.entity.*;
import com.ecommerce.id.vn.repository.CartItemRepository;
import com.ecommerce.id.vn.repository.OrderDetailRepository;
import com.ecommerce.id.vn.repository.OrderRepository;
import com.ecommerce.id.vn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    //Hàm chuyển đổi
    private String convertImage(byte[] imageProduc) {
        String base64Image = null;

        if (imageProduc != null) {
            base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageProduc);

        }
        return base64Image;
    }

    @Override
    @Transactional
    public Optional<OrderResponseDTO> crateOrder(OrderRequestDTO orderDTO) {
        try {
            Optional<User> userOptional = userRepository.findById(orderDTO.getUserId());

            return userOptional.map(user -> {
                Order order = createOrderFromDTO(orderDTO, user);
                orderRepository.save(order);

                List<CartItem> cartItems = cartItemRepository.findByUserId(orderDTO.getUserId());
                List<OrderDetail> orderDetails = createOrderDetailsFromCartItems(cartItems, order);

                orderDetailRepository.saveAll(orderDetails);

                List<OrderDetailResponseDTO> orderDetailResponseDTOs = createOrderDetailResponseDTOs(orderDetails);

                OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
                        order.getId(),
                        order.getCodeOrder(),
                        order.getOrdeDate(),
                        order.getNameConsignee(),
                        order.getShippingAddress(),
                        order.getPhoneOder(),
                        order.getTotalAmount(),
                        order.getStatusPayment(),
                        order.getStatusOrder(),
                        orderDetailResponseDTOs
                );
                // Xóa các sản phẩm trong giỏ hàng sau khi đặt hàng thành công
                cartItemRepository.deleteByUserId(orderDTO.getUserId());
                return Optional.of(orderResponseDTO);
            }).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            e.printStackTrace(); // In real applications, consider using a logging framework
            return Optional.empty();
        }
    }

    private Order createOrderFromDTO(OrderRequestDTO orderDTO, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setOrdeDate(LocalDate.now());
        order.setNameConsignee(orderDTO.getNameConsignee());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setPhoneOder(orderDTO.getPhoneOrder());
        //order.setTotalAmount(orderDTO.getTotalAmount());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatusPayment(orderDTO.getPayment());
        order.setStatusOrder(orderDTO.getStatus());
        order.setCodeOrder(orderDTO.getCodeOrder());
        return order;
    }

    private List<OrderDetail> createOrderDetailsFromCartItems(List<CartItem> cartItems, Order order) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProducts());
            orderDetail.setPrice(BigDecimal.valueOf(cartItem.getProducts().getPrice()));
            orderDetail.setQuantity(cartItem.getQuantity());
            BigDecimal totalPrice = BigDecimal.valueOf(cartItem.getProducts().getPrice() * cartItem.getQuantity());
            orderDetail.setTotalPrice(totalPrice);
            //orderDetail.setPhotoProduct("/images/" + cartItem.getProducts().getPhoto());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    private List<OrderDetailResponseDTO> createOrderDetailResponseDTOs(List<OrderDetail> orderDetails) {
        List<OrderDetailResponseDTO> orderDetailResponseDTOs = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            OrderDetailResponseDTO orderDetailResponseDTO = new OrderDetailResponseDTO();
//            orderDetailResponseDTO.setPhotoProduct(orderDetail.getProduct().getImage());
            orderDetailResponseDTO.setPhotoProduct(convertImage(orderDetail.getProduct().getImage()));
            orderDetailResponseDTO.setProductName(orderDetail.getProduct().getBookName());
            orderDetailResponseDTO.setPrice(orderDetail.getPrice());
            orderDetailResponseDTO.setQuantity(orderDetail.getQuantity());
            orderDetailResponseDTO.setTotalPrice(orderDetail.getTotalPrice());
            orderDetailResponseDTOs.add(orderDetailResponseDTO);
        }
        return orderDetailResponseDTOs;
    }

    @Override
    @Transactional
    public Optional<List<OrderResponseDTO>> viewOrdersByUserId(Integer userId) {
        try {
            List<Order> orders = orderRepository.findByUserId(userId);
            if (orders.isEmpty()) {
                return Optional.empty();
            }

            List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();
            for (Order order : orders) {
                List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
                List<OrderDetailResponseDTO> orderDetailResponseDTOs = createOrderDetailResponseDTOs(orderDetails);

                OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
                        order.getId(),
                        order.getCodeOrder(),
                        order.getOrdeDate(),
                        order.getNameConsignee(),
                        order.getShippingAddress(),
                        order.getPhoneOder(),
                        order.getTotalAmount(),
                        order.getStatusPayment(),
                        order.getStatusOrder(),
                        orderDetailResponseDTOs
                );

                orderResponseDTOs.add(orderResponseDTO);
            }

            return Optional.of(orderResponseDTOs);
        } catch (Exception e) {
            e.printStackTrace(); // In real applications, consider using a logging framework
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public String updateStatusPayment( String codeOrder, String statusPayment) {
        try {
            Optional<Order> optionalOrder = orderRepository.findByCodeOrder(codeOrder);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setStatusPayment(statusPayment);
                System.out.println("trang thái đơn hàng" + statusPayment );
                orderRepository.save(order);
                return "Cập nhật đơn hàng thành công";
            } else {
                throw new RuntimeException("Order not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Không tìm thấy đơn hàng";
        }
    }

    @Override
    public String updateStatusOrder(int orderId, String statusOrder) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setStatusOrder(statusOrder);
                orderRepository.save(order);
                return "Cập nhật đơn hàng thành công";
            } else {
                throw new RuntimeException("Order not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Không tìm thấy đơn hàng";
        }
    }

    @Override
    public List<OrderResponseDTO> getAllOrder() {
        try {
            List<Order> orders = orderRepository.findAll();
            List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
            for (Order order : orders) {
                OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
                orderResponseDTO.setOrderId(order.getId());
                //check
                System.out.println(order.getId());

                orderResponseDTO.setCodeOrder(order.getCodeOrder());
                orderResponseDTO.setOrderDate(order.getOrdeDate());
                orderResponseDTO.setNameConsignee(order.getNameConsignee());
                orderResponseDTO.setShippingAddress(order.getShippingAddress());
                orderResponseDTO.setPhoneOrder(order.getPhoneOder());
                orderResponseDTO.setTotalAmount(order.getTotalAmount());
                orderResponseDTO.setStatusPayment(order.getStatusPayment());
                orderResponseDTO.setStatusOrder(order.getStatusOrder());

                List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());

                List<OrderDetailResponseDTO> orderDetailResponseDTOs = createOrderDetailResponseDTOs(orderDetails);

                orderResponseDTO.setOrderDetailResponseDTO(orderDetailResponseDTOs);

                orderResponseDTOList.add(orderResponseDTO);
            }
            return orderResponseDTOList;

        } catch (Exception e) {
            throw new RuntimeException("Lỗi truy xuất dữ liệu từ database", e);
        }
    }
}
