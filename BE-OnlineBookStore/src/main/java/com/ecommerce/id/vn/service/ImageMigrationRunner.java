//package com.ecommerce.id.vn.service;
//
//import com.ecommerce.id.vn.entity.Product;
//import com.ecommerce.id.vn.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.List;
//
//@Component
//public class ImageMigrationRunner implements CommandLineRunner {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        saveImage();
//    }
//
//    public void saveImage() {
//
//        List<Product> productList = productRepository.findAll();
//
//        for (Product product : productList) {
//            // Lấy tên ảnh từ cơ sở dữ liệu
//            String photoName = product.getPhoto();
//            // Đường dẫn tới thư mục chứa ảnh
//            String directoryPath = "D:\\projeck-intem\\BE-local - Copy\\uploads\\images";
//
//            // Tạo đường dẫn đầy đủ tới file ảnh
//            String fullPath = directoryPath + File.separator + photoName;
//
//            try {
//                // Đọc ảnh từ file và lưu dưới dạng byte[]
//                byte[] imageData = Files.readAllBytes(Paths.get(fullPath));
//
//                // Lưu ảnh vào trường 'image' trong đối tượng Product
//                product.setImage(imageData);
//
//                // Cập nhật lại thông tin Product trong cơ sở dữ liệu
//                productRepository.save(product);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                // Xử lý ngoại lệ nếu file không tồn tại hoặc không đọc được
//            }
//        }
//    }
//}
