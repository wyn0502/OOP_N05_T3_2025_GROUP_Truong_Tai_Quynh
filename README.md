<h1 align="center">🐾 Ứng Dụng Quản Lý Sở Thú (Zoo Management System)</h1>

<p align="center">
  📚 <strong>Java OOP Project</strong> – Nhóm: <code>OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh</code><br>
  🎓 Môn học: Lập trình Hướng Đối Tượng (OOP)<br>
  👩‍🏫 Giảng viên hướng dẫn: TS. Nguyễn Lệ Thu<br>
  📅 Học kỳ III Năm học 2024-2025
</p>

---

## 📑 Mục lục
1. 📖 Giới thiệu
2. 🎯 Mục tiêu và phạm vi
3. ⚙️ Yêu cầu chức năng
4. 🏗️ Phân tích và thiết kế hướng đối tượng
   - 4.1. 📦 Các class chính
   - 4.2. 🔗 Mối quan hệ giữa các class
5. 💡 Các nguyên lý OOP áp dụng
6. 📊 Sơ đồ Thiết kế hệ thống
    - 6.1. 📐 Class diagram
    - 6.2. 🔁 Activity diagrams
8. 🗂️ Cấu trúc thư mục & mô tả file quan trọng
9. ▶️ Hướng dẫn chạy chương trình
   - 8.1. Chạy bằng IDE
   - 8.2. Chạy bằng Maven CLI
   - 8.3. Chạy bằng Codespaces
10. 🧪 Test & ví dụ đầu vào/đầu ra
11. 🚀 Ghi chú triển khai và hướng phát triển tiếp
12. 👨‍👩‍👧‍👦 Thành viên nhóm
13. 📚 Tài liệu tham khảo & link repository

---

## 1. 📌 Giới thiệu dự án

🎯 Đây là một ứng dụng mô phỏng hệ thống quản lý trong một sở thú, nơi người dùng có thể:
- Quản lý thông tin các 🦁 động vật (mã, tên, loại, tuổi, chuồng...).
- Theo dõi chuồng 🏠 (sức chứa, khu vực, số lượng hiện tại).
- Ghi nhận nhân viên 👨‍🌾 chăm sóc.
- Quản lý lịch cho ăn 🍽️ theo ngày giờ và loại thức ăn.
- Quản lý giá vé 🎫 khi tham gia sở thú.

🔧 Dự án được phát triển bằng **Java (OOP)**, với trọng tâm là:
- 🎯 Luyện tập các khái niệm: **Class, Đối tượng, Đóng gói, Kế thừa, Đa hình**.
- 🔁 Mô hình hóa sát thực tế với các **class**: ***DongVat, Chuong, NhanVien, LichChoAn, GiaVe***.

## 2. 🎯 Mục tiêu và phạm vi
- 🐵 Mô tả, lưu trữ và thao tác thông tin động vật.
- 🏠 Quản lý chuồng nuôi.
- 👨‍🌾 Quản lý nhân viên chăm sóc.
- 🥕 Quản lý lịch cho ăn.
- 🎟️ Tính toán giá vé.

## 3. ⚙️ Yêu cầu chức năng
- ➕➖ Thêm / sửa / xóa / tìm kiếm động vật.
- 🏠 Thêm / sửa / xóa / kiểm tra chuồng.
- 👨‍🌾 Thêm / sửa / gán nhân viên cho động vật.
- 📅 Lên lịch cho ăn.
- 💰 Tính tiền vé.

## 4. 🏗️ Phân tích và thiết kế hướng đối tượng
### 4.1. 🧱 Main class

| 📦 Class              | 📝 Mô tả chức năng |
|------------------------|--------------------|
| `DongVat`              | Quản lý thông tin động vật: mã, tên, loài, tuổi, chuồng. |
| `Chuong`               | Quản lý chuồng nuôi: mã chuồng, khu vực, sức chứa, số lượng hiện tại. |
| `NhanVien`             | Lưu thông tin nhân viên chăm sóc: mã, họ tên, tuổi, danh sách chuồng phụ trách. |
| `GiaVe`                | Áp dụng giảm cho mỗi đối tượng: trẻ em, sinh viên, người lớn, người già, đặc biệt. |
| `LichChoAn`            | Quản lý lịch cho ăn: ngày giờ, loại thức ăn, mã thú. |

### 4.2. 🔗 Mối quan hệ giữa các class
- `DongVat` → `Chuong`
- `NhanVien` → `DongVat`
- `LichChoAn` ↔ `DongVat`

## 5. 💡 Các nguyên lý OOP áp dụng
- 🔒 **Đóng gói (Encapsulation)**
- 🧬 **Kế thừa (Inheritance)**
- 🎭 **Đa hình (Polymorphism)**
- 📜 **Trừu tượng (Abstraction)**


## 6. 📊 Sơ đồ Thiết kế Hệ thống

### 6.1. 📐 Class Diagram
<img width="2048" height="509" alt="Image" src="https://github.com/user-attachments/assets/7dcd7797-b558-4564-8bc6-315fc68e5d84" />

### 6.2. 🔁 Activity Diagrams
<details>
<summary>Click để xem các sơ đồ</summary>

<img width="582" height="737" alt="Activity Diagram 1" src="https://github.com/user-attachments/assets/4b13c5e2-b075-4019-8df4-849b0b95ba85" />
Activity GiaVe
<img width="393" height="706" alt="Activity Diagram 2" src="https://github.com/user-attachments/assets/bc42dae3-ffb3-4eb5-889c-82c032fc2acf" />
Activity Chuong
<img width="629" height="683" alt="Activity Diagram 3" src="https://github.com/user-attachments/assets/946fc813-4757-4e8d-be5f-1b1838697849" />
Activity LichChoAn
<img width="533" height="789" alt="Activity Diagram 4"src="https://github.com/user-attachments/assets/27379e37-8611-40f0-ad22-746c08189bfd" />
Activity DongVat
<img width="857" height="736" alt="Activity Diagram 5" src="https://github.com/user-attachments/assets/2a66e0e5-fd64-48c5-898c-70276ae92f37" />
Activity
</details>

## 7. 🗂️ Cấu trúc thư mục

OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh/<br>
└── ZooManagementSystem/<br>
    ├── src/main/java/com/example/zoo/<br>
    │   │                   ├── ZooManagementApplication.java     # Main Spring Boot App<br>
    │   │                   ├── GreetingController.java<br>
    │   │                   ├── controller/                      # REST Controllers<br>
    │   │                   │   ├── ChuongController.java<br>
    │   │                   │   ├── DongVatController.java<br>
    │   │                   │   ├── GiaVeController.java<br>
    │   │                   │   ├── LichChoAnController.java<br>
    │   │                   │   ├── NhanVienController.java<br>
    │   │                   │   ├── LoginController.java<br>
    │   │                   │   ├── RegisterController.java<br>
    │   │                   │   ├── RedirectController.java<br>
    │   │                   │   └── ErrorControllerCustom.java<br>
    │   │                   ├── model/                           # Entity Classes<br>
    │   │                   │   ├── DongVat.java<br>
    │   │                   │   ├── Chuong.java<br>
    │   │                   │   ├── NhanVien.java<br>
    │   │                   │   ├── LichChoAn.java<br>
    │   │                   │   ├── GiaVe.java<br>
    │   │                   │   ├── User.java<br>
    │   │                   │   └── RegisterForm.java<br>
    │   │                   ├── service/                         # Business Logic<br>
    │   │                   │   ├── DongVatService.java<br>
    │   │                   │   ├── ChuongService.java<br>
    │   │                   │   ├── NhanVienService.java<br>
    │   │                   │   ├── LichChoAnService.java<br>
    │   │                   │   └── GiaVeService.java<br>
    │   │                   ├── repository/                      # Data Access Layer<br>
    │   │                   │   ├── DongVatRepository.java<br>
    │   │                   │   ├── ChuongRepository.java<br>
    │   │                   │   ├── NhanVienRepository.java<br>
    │   │                   │   ├── LichChoAnRepository.java<br>
    │   │                   │   ├── GiaVeRepository.java<br>
    │   │                   │   └── UserRepository.java<br>
    │   │                   ├── interfaces/                      # Interface Definitions<br>
    │   │                   │   ├── IHasId.java<br>
    │   │                   │   └── IManager.java<br>
    │   │                   └── database/                        # Database Configuration<br>
    │   │                       ├── MyDBConnection.java<br>
    │   │                       └── AivenConnection.java<br>
    │   └── test/java/com/example/zoo/<br>
    │                       ├── ServingWebContentApplicationTest.java<br>
    │                       └── test/                            # Unit Tests<br>
    │                           ├── DongVatControllerTest.java<br>
    │                           ├── DongVatServiceTest.java<br>
    │                           ├── ChuongControllerTest.java<br>
    │                           ├── ChuongServiceTest.java<br>
    │                           ├── GiaVeControllerTest.java<br>
    │                           ├── GiaVeServiceTest.java<br>
    │                           └── GiaVeTest.java<br>
    └── review/Start/test/                                                  # Development/Testing Files<br>
            ├── quynh/<br>
            ├── tai/<br>
            └── truong/<br>

## 8. ▶️ Hướng dẫn chạy
### 8.1 Chạy bằng IDE (IntelliJ IDEA / Eclipse)
1. Clone repo:  
   ```bash
   git clone https://github.com/wyn0502/OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh.git
   ```
2. Tải extention phù hợp
3. Tạo PORT 8088 hoặc khác nếu muốn
4. Run
### 8.2 Chạy bằng Maven CLI
```bash
  git clone https://github.com/wyn0502/OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh.git

  cd ZooManagementSystem

  mvn clean compile exec:java
```
### 8.3 Chạy trực tiếp bằng Codespaces
  - Tạo Codespaces
  - Tải extention Java
  - Run

## 9. 🧪 Test
- JUnit cho các phương thức CRUD, kiểm tra sức chứa, tính giá vé.
- Unit test đầy đủ

## 10. 🚀 Hướng phát triển
- 💾 Lưu dữ liệu vào DataBase (sql)
- 🖥️ Giao diện GUI/REST API
- 🛡️ Validation & Exception handling
  
---

## 👨‍👩‍👧‍👦 Thành viên nhóm

| Tên thành viên        | Mã SV      | GitHub                                            |
|------------------------|------------|----------------------------------------------------|
| 🧑‍💻 Lò Tuấn Quỳnh       | 24104502   | [@wyn0502](https://github.com/wyn0502)             |
| 👨‍💻 Vũ Xuân Trường      | 24107720   | [@xuantruong1612](https://github.com/xuantruong1612) |
| 👨‍💻 Đặng Đức Tài         | 24107665   | [@dangtai-0510](https://github.com/dangtai-0510)     |

---

## 🔗 Link repository

📂 **Source Code:**  
👉 [`github.com/wyn0502/OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh`](https://github.com/wyn0502/OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh)

📁 **Thư mục chứa mã nguồn chính:**  
👉 [`/ZooManagementSystem/src/main/java/com/example/zoo`](https://github.com/wyn0502/OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh/tree/main/ZooManagementSystem/src/main/java/com/example/zoo)

**Link web:**
[`WEB`](https://wyn0502.id.vn/login)
---

💡 **Nếu bạn thấy dự án hữu ích, hãy nhấn 🌟 star để ủng hộ nhóm nhé!**
