# Leave-Request-Approval-Systerm
A microservices-based system built with Spring Boot, Docker, and Spring Cloud. Includes services for authentication, user management, and business logic.

### Cài Đặt Môi Trường Dev

1. Clone repository:
   ```bash
   git clone https://github.com/ptt3123/Leave-Request-Approval-Systerm.git
   ```

2. Thiết lập biến môi trường:
   ```bash
   # Coppy .env.example sang .env
   cp .env.example .env
   
   # Chỉnh sửa file .env với thông tin cần thiết
   ```

3. Build hệ thống:
   ```bash
   docker-compose up --build
   ```
4. Chạy hệ thống:
    ```bash
   # Cấp quền cho file init.sh
   chmod +x init.sh
   
   # Chạy file init.sh
   ./scripts/init.sh
   ```

5. Truy cập hệ thống:
    - Backend API: http://localhost:8080

## 📚 Tài Liệu

- Tài liệu API: `/docs/api-specs/`
- Phân tích và thiết kế: `/docs/analysis-and-design.md`
- Kiến trúc hệ thống: `/docs/architecture.md`

## 📜 Giấy Phép

Dự án này được phát triển dựa trên template của Hung Dang (hungdn@ptit.edu.vn, GitHub: hungdn1701).