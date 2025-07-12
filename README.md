<h1 align="center">🐾 Ứng Dụng Quản Lý Sở Thú</h1>

<p align="center">
  📚 <strong>Java OOP Project</strong> – Nhóm: <code>OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh</code><br>
  🎓 Môn học: Lập trình Hướng Đối Tượng – Năm nhất – Khoa CNTT
</p>

---

## 📌 Giới thiệu dự án

🎯 Đây là một ứng dụng mô phỏng hệ thống quản lý trong một sở thú, nơi người dùng có thể:
- Quản lý thông tin các 🦁 động vật (mã, tên, loại, tuổi, chuồng...).
- Theo dõi chuồng 🏠 (sức chứa, khu vực, số lượng hiện tại).
- Ghi nhận nhân viên 👨‍🌾 chăm sóc.
- Quản lý lịch cho ăn 🍽️ theo ngày giờ và loại thức ăn.
- Quản lý giá vé 🎫 khi tham gia sở thú.

🔧 Dự án được phát triển bằng **Java (OOP)**, với trọng tâm là:
- 🎯 Luyện tập các khái niệm: **Class, Đối tượng, Đóng gói, Kế thừa, Đa hình**.
- 🔁 Mô hình hóa sát thực tế với các **class**: ***DongVat, Chuong, NhanVien, LichChoAn, GiaVe***.

# Sơ đồ Class Diagram
<img width="677" height="748" alt="Image" src="https://github.com/wyn0502/OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh/blob/main/ZooManagementSystem/image/class%20diagram.png?raw=true"/>

---

## 🧱 Main class

| 📦 Class    | 📝 Mô tả chức năng                                                                                                                                                                  |
| ----------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `DongVat`   | Lưu thông tin động vật: mã, tên, loài, tuổi, chuồng, giờ ăn.                                                                                                                        |
| `Chuong`    | Quản lý chuồng nuôi: khu vực, sức chứa, số lượng hiện tại.<br>🛠️ Có các constructor hỗ trợ khởi tạo với các tham số khác nhau và hàm `HienThiThongTin()` in thông tin chuồng.      |
| `GiaVe`     | Quản lý giá vé theo từng đối tượng:<br> ➤ Trẻ em (giảm 50%)<br> ➤ Sinh viên (giảm 30%)<br> ➤ Người già (giảm 40%)<br> ➤ Người lớn (giá gốc)<br>➕ Tính tổng tiền, hỗ trợ khuyến mãi. |
| `NhanVien`  | Thông tin nhân viên: mã, tên, số điện thoại, chuồng phụ trách.                                                                                                                      |
| `LichChoAn` | Ghi nhận lịch cho ăn theo ngày giờ và loại thức ăn cho từng động vật.                                                                                                               |


---

## 👨‍👩‍👧‍👦 Thành viên nhóm

| Tên thành viên        | Mã SV      | GitHub                      |
|------------------------|------------|------------------------------|
| 🧑‍💻 Lò Tuấn Quỳnh       | 24104502   | [@wyn0502](https://github.com/wyn0502) |
| 👨‍💻 Vũ Xuân Trường      | 24107720   | [@xuantruong1612](https://github.com/xuantruong1612) |
| 👨‍💻 Đặng Đức Tài         | 24107665   | [@dangtai-0510](https://github.com/dangtai-0510)     |

---

## 🔗 Link repo

📂 Repository GitHub:  
👉 [github.com/wyn0502/OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh](https://github.com/wyn0502/OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh)
