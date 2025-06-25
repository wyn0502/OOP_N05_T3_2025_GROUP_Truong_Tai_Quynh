<h1 align="center">🐾 Ứng Dụng Quản Lý Sở Thú - Zoo Management System</h1>

<p align="center">
  📚 <strong>Java OOP Project</strong> – Nhóm: <code>OOP_N05_T3_2025_GROUP_Truong_Tai_Quynh</code><br>
  🎓 Môn học: Lập trình Hướng Đối Tượng – Năm nhất – Khoa CNTT
</p>

---

## 📌 Nội dung bài tập lớn

### Câu 1: Tiêu đề dự án
**Ứng dụng quản lý sở thú - Zoo Management System**

### Câu 2: Phân tích đối tượng
## 🔍 Câu 2: Phân tích 03 đối tượng chính

### 1. Động vật (DongVat)
| Thành phần       | Chi tiết                                                                 |
|------------------|--------------------------------------------------------------------------|
| **Mục đích**     | Quản lý thông tin và hành vi của các cá thể động vật trong sở thú         |
| **Thuộc tính**   | `maDongVat`, `ten`, `loai`, `tuoi`, `chuong`, `thoiGianChoAn`           |
| **Phương thức**  | `diChuyenChuong()`, `kiemTraSucKhoe()`, `capNhatThongTin()`             |
| **Quan hệ**      | Thuộc về 1 `Chuong`, được chăm sóc bởi nhiều `NhanVien`                 |

### 2. Chuồng (Chuong)
| Thành phần       | Chi tiết                                                                 |
|------------------|--------------------------------------------------------------------------|
| **Mục đích**     | Quản lý không gian sống và điều kiện nuôi nhốt động vật                  |
| **Thuộc tính**   | `maChuong`, `khuVuc`, `sucChua`, `soLuongHienTai`                       |
| **Phương thức**  | `themDongVat()`, `kiemTraConChoTrong()`, `veSinhChuong()`               |
| **Quan hệ**      | Chứa nhiều `DongVat`, được quản lý bởi nhiều `NhanVien`                 |

### 3. Nhân viên (NhanVien)
| Thành phần       | Chi tiết                                                                 |
|------------------|--------------------------------------------------------------------------|
| **Mục đích**     | Quản lý thông tin và công việc của nhân sự trong sở thú                  |
| **Thuộc tính**   | `maNhanVien`, `ten`, `soDT`, `chuongPhuTrach`                           |
| **Phương thức**  | `phanCongChuong()`, `xemLichLamViec()`, `baoCaosuCo()`                  |
| **Quan hệ**      | Phụ trách nhiều `Chuong`, chăm sóc nhiều `DongVat`                       |

### Câu 3: Cấu trúc thư mục
````

ZooManagementSystem/
├── .vscode/               # Cấu hình VS Code
├── bin/                   # Thư mục chứa file .class
│   └── ZooManagementSystem/
│       └── src/          
│           ├── App.class
│           ├── Chuong.class
│           ├── DongVat.class
│           ├── GiaVe.class
│           ├── LichChoAn.class
│           ├── NhanVien.class
│           ├── TestGiaVe.class
│           └── ... 
└── src/                 
    ├── App.java
    ├── Chuong.java
    ├── DongVat.java
    ├── GiaVe.java
    ├── LichChoAn.java
    ├── NhanVien.java
├── review/
    └── for-while-do/
        ├── BreakAndContinue.java
        ├── TestWhile.java
    └── Learn/xuantruong_is_here/
        ├── ABC.java
        ├── for-while-do.md
        ├── NhapTenTuoi.java
    └── Test/
        ├── MyNumber.java
        ├── PassObject.java
        ├── TestGiaVe.java

````
---

## 🧱 Main class

| 📦 Class       | 📝 Mô tả chức năng                                                                 |
|----------------|-------------------------------------------------------------------------------------|
| `DongVat`      | Lưu thông tin động vật: mã, tên, loài, tuổi, chuồng, giờ ăn.                        |
| `Chuong`       | Quản lý chuồng nuôi: khu vực, sức chứa, số lượng hiện tại.                          |
| `NhanVien`     | Thông tin nhân viên: mã, tên, sđt, chuồng phụ trách.                                |
| `LichChoAn`    | Ghi nhận lịch cho ăn theo ngày giờ và loại thức ăn.                                 |
| `GiaVe`        | Quản lí giá vé như học sinh, sinh viên, người lớn, trẻ em,...                       |

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
