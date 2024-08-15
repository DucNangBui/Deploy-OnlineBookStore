
# Sử dụng hình ảnh JDK 17 làm base image
FROM eclipse-temurin:17-jdk-alpine

# Đặt biến môi trường
ENV SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD
# Đặt biến môi trường FRONTEND_URL
ENV FRONTEND_URL=$FRONTEND_URL
# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR từ máy local vào container
COPY target/OnlineBookStore-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng để ứng dụng lắng nghe
EXPOSE 8081

# Lệnh để chạy ứng dụng Spring Boot
ENTRYPOINT ["java","-jar","/app/app.jar"]
