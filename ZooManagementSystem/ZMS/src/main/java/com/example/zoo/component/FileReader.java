package com.example.zoo.component;

import java.io.File;
import java.io.FileInputStream;

import org.hibernate.stat.Statistics;

public class FileReader{
    public static void main(String[] args) {
        File f = new File("final/src/test.txt");
        try (FileInputStream fis = new FileInputStream(f)) {
            int c;
            while ((c = fis.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
<!DOCTYPE html>
<html lang="vi" xmlns:th="http://www.thymeleaf.org">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Zoo Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: #333;
        }

        .gradient-card {
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(10px);
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .gradient-card:hover {
            transform: scale(1.02);
        }

        .card-header-icon {
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            color: #fff;
        }

        .btn-gradient {
            background: linear-gradient(135deg, #5a4fcf, #3a6bb4, #1ca8a0);
            color: white;
            border: none;
        }

        .btn-gradient:hover {
            filter: brightness(1.1);
        }

        .main-rectangle {
            min-height: 650px;
            display: flex;
            flex-direction: column;
        }

        .header-bar {
            margin-bottom: 1.5rem;
        }

        .top-section {
            margin-bottom: 2.5rem;
        }

        .bottom-section {
            display: flex;
            gap: 2.5rem;
            align-items: flex-start;
        }

        .left-info {
            flex: 2;
            min-width: 0;
            display: flex;
            flex-direction: column;
            gap: 2rem;
        }

        .right-actions {
            flex: 1;
            min-width: 260px;
            display: flex;
            flex-direction: column;
        }

        .card-4grid .card {
            border-radius: 10px;
        }

        @media (max-width: 992px) {
            .main-rectangle {
                min-height: unset;
            }

            .header-bar {
                margin-bottom: 1rem;
            }

            .top-section {
                margin-bottom: 1rem;
            }

            .bottom-section {
                flex-direction: column;
                gap: 1.2rem;
            }

            .left-info,
            .right-actions {
                flex: none;
            }
        }
    </style>
</head>

<body>
    <div class="container py-4">
        <div class="main-rectangle mx-auto" style="max-width: 1280px;">
            <div class="header-bar d-flex justify-content-between align-items-center">
                <div>
                    <h2 class="text-white fw-bold">Zoo Management System</h2>
                    <small class="text-white-50">Hệ thống quản lý sở thú</small>
                </div>
                <div class="text-end">
                    <div class="badge bg-warning text-dark mb-1">
                        <i class="fas fa-user"></i>
                        <span th:text="${name}">Tên nhân viên</span>
                    </div>
                    <div class="badge bg-white text-dark">
                        <i class="fas fa-clock me-1"></i>
                        <span id="liveTime"></span>
                    </div>
                </div>
            </div>
            <div class="top-section">
                <div class="card gradient-card p-4">
                    <div class="row g-3 row-cols-2 row-cols-md-auto">
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-primary mb-2 mx-auto">
                                    <i class="fas fa-paw"></i>
                                </div>
                                <h4 th:text="${dongVatCount}">0</h4>
                                <small class="text-muted">Động vật</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-success mb-2 mx-auto">
                                    <i class="fas fa-ticket-alt"></i>
                                </div>
                                <h4 th:text="${loaiVeCount}">0</h4>
                                <small class="text-muted">Loại vé</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-danger mb-2 mx-auto">
                                    <i class="fas fa-heart"></i>
                                </div>
                                <h4 th:text="${tongNhanVien}">0</h4>
                                <small class="text-muted">Tổng nhân viên</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-secondary mb-2 mx-auto">
                                    <i class="fas fa-house-damage"></i>
                                </div>
                                <h4 th:text="${chuongCount}">0</h4>
                                <small class="text-muted">Chuồng</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-info mb-2 mx-auto">
                                    <i class="fas fa-calendar-alt"></i>
                                </div>
                                <h4 th:text="${lichChoAnCount}">0</h4>
                                <small class="text-muted">Lịch cho ăn</small>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="bottom-section">
                <div class="left-info">
                    <div class="card gradient-card p-4 card-4grid">
                        <div class="row g-3">
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div class="card-header bg-light fw-bold">
                                        <i class="fas fa-user-tie me-2"></i>Thông tin nhân viên
                                    </div>
                                    <ul class="list-group list-group-flush" th:each="nv : ${danhSachNhanVien}">
                                        <li class="list-group-item">
                                            <div class="fw-semibold" th:text="${nv.fullname}">Tên</div>
                                            <div class="small text-muted">Mã nhân viên: <span
                                                    th:text="${nv.id}">1</span></div>
                                            <div class="small">Tài khoản: <span th:text="${nv.username}">admin</span>
                                            </div>
                                            <div class="small">Ngày vào làm: <span
                                                    th:text="${nv.datework}">2025-08-05</span></div>
                                            <div class="small">SĐT: <span th:text="${nv.phone}">0341234567</span></div>
                                            <div class="small">Chuồng: <span th:text="${nv.chuong}">Capybara</span>
                                            </div>
                                            <div class="small">Cấp bậc: <span th:text="${nv.role}">admin</span></div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div
                                        class="card-header bg-light fw-bold d-flex justify-content-between align-items-center">
                                        <span><i class="fas fa-paw me-2"></i>Thông tin động vật</span>
                                        <form method="get" action="/greeting" class="d-inline">
                                            <select name="dongVatId" class="form-select form-select-sm d-inline w-auto"
                                                onchange="this.form.submit()"
                                                th:if="${not #lists.isEmpty(danhSachDongVat)}">
                                                <option th:each="dv : ${danhSachDongVat}" th:value="${dv.id}"
                                                    th:text="${dv.ten}" th:selected="${dv.id == dongVat?.id}">Động vật
                                                </option>
                                            </select>
                                        </form>
                                    </div>
                                    <ul class="list-group list-group-flush" th:if="${dongVat != null}">
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Tên</span><span th:text="${dongVat.ten}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Tuổi</span><span th:text="${dongVat.tuoi + ' tuổi'}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Loài</span><span th:text="${dongVat.loai}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Sức khỏe</span>
                                            <span th:text="${dongVat.sucKhoe}"
                                                th:classappend="${dongVat.sucKhoe.toLowerCase().contains('khỏe') ? 'text-success' : 'text-danger'}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Khu vực</span><span
                                                th:text="${dongVat.khuVuc != null ? dongVat.khuVuc : 'Không xác định'}">...</span>
                                        </li>
                                    </ul>
                                    <div class="p-2 text-danger fw-bold" th:if="${dongVat == null}">
                                        Không có dữ liệu động vật trong hệ thống!
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div class="card-header bg-light fw-bold">
                                        <i class="fas fa-ticket-alt me-2"></i>Thông tin giá vé
                                    </div>
                                    <ul class="list-group list-group-flush"
                                        th:if="${not #lists.isEmpty(danhSachGiaVe)}">
                                        <li class="list-group-item" th:each="ve : ${danhSachGiaVe}">
                                            <div class="fw-semibold mb-1" th:text="${ve.loaiVe}">Vé Loại</div>
                                            <div class="d-flex justify-content-between small">
                                                <span>Giá gốc:</span>
                                                <span
                                                    th:text="${#numbers.formatDecimal(ve.giaCoBan, 0, 'POINT', 0, 'COMMA')} + ' VNĐ'">0
                                                    VNĐ</span>
                                            </div>
                                            <div class="d-flex justify-content-between small"
                                                th:if="${ve.phanTramGiamGia > 0}">
                                                <span>Giảm giá:</span>
                                                <span class="text-warning"
                                                    th:text="${ve.phanTramGiamGia + '%'}">0%</span>
                                            </div>
                                            <div class="d-flex justify-content-between small"
                                                th:if="${ve.phanTramGiamGia > 0}">
                                                <span>Giá sau giảm:</span>
                                                <span class="text-success"
                                                    th:text="${#numbers.formatDecimal(ve.giaCoBan * (1 - ve.phanTramGiamGia / 100), 0, 'POINT', 0, 'COMMA')} + ' VNĐ'">0
                                                    VNĐ</span>
                                            </div>
                                            <hr class="my-2">
                                        </li>
                                    </ul>
                                    <div class="p-2 text-danger" th:if="${#lists.isEmpty(danhSachGiaVe)}">
                                        Không có vé nào trong hệ thống.
                                    </div>
                                </div>
                            </div>
                            <!-- Phải dưới: Chuồng -->
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div
                                        class="card-header bg-light fw-bold d-flex justify-content-between align-items-center">
                                        <span><i class="fas fa-house-damage me-2"></i>Thông tin chuồng</span>
                                        <form method="get" action="/greeting" class="d-inline">
                                            <select name="chuongId" class="form-select form-select-sm d-inline w-auto"
                                                onchange="this.form.submit()"
                                                th:if="${not #lists.isEmpty(danhSachChuong)}">
                                                <option th:each="c : ${danhSachChuong}" th:value="${c.maChuong}"
                                                    th:text="${c.tenKhuVuc + ' (' + c.maChuong + ')'}"
                                                    th:selected="${c.maChuong == chuong?.maChuong}">Chuồng</option>
                                            </select>
                                        </form>
                                    </div>
                                    <ul class="list-group list-group-flush" th:if="${chuong != null}">
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Mã chuồng</span><span th:text="${chuong.maChuong}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Khu vực</span><span th:text="${chuong.tenKhuVuc}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Sức chứa</span><span th:text="${chuong.sucChuaToiDa}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Số lượng hiện tại</span><span
                                                th:text="${chuong.soLuongHienTai}">...</span>
                                        </li>
                                    </ul>
                                    <div class="p-2 text-danger fw-bold" th:if="${chuong == null}">
                                        Không có dữ liệu chuồng trong hệ thống!
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- BÊN PHẢI: Thao tác nhanh -->
                <div class="right-actions">
                    <div class="card gradient-card p-4 d-flex flex-column align-items-stretch">
                        <h5 class="text-center fw-bold mb-3"><i class="fas fa-rocket me-2"></i>Thao tác nhanh</h5>
                        <div class="d-flex flex-column gap-3">
                            <a href="/nhanvien" class="btn btn-gradient w-100"><i class="fas fa-user-tie me-1"></i>Quản
                                lý Nhân viên</a>
                            <a href="/chuong" class="btn btn-gradient w-100"><i class="fas fa-house-damage me-1"></i>
                                Quản lý Chuồng</a>
                            <a href="/dongvat" class="btn btn-gradient w-100"><i class="fas fa-paw me-1"></i> Quản lý
                                Động Vật</a>
                            <a href="/giave" class="btn btn-gradient w-100"><i class="fas fa-ticket-alt me-1"></i> Quản
                                lý Giá Vé</a>
                            <a href="/lichchoan" class="btn btn-gradient w-100"><i
                                    class="fas fa-calendar-check me-1"></i>Quản lý Lịch Cho Ăn</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function updateTime() {
            const now = new Date();
            const options = { year: "numeric", month: "2-digit", day: "2-digit", hour: "2-digit", minute: "2-digit", second: "2-digit", hour12: false };
            const formatter = new Intl.DateTimeFormat("vi-VN", options);
            document.getElementById("liveTime").textContent = formatter.format(now).replace(",", "");
        }
        updateTime();
        setInterval(updateTime, 1000);
    </script>
</body>

</html>
<!DOCTYPE html>
<html lang="vi" xmlns:th="http://www.thymeleaf.org">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Zoo Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: #333;
        }

        .gradient-card {
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(10px);
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .gradient-card:hover {
            transform: scale(1.02);
        }

        .card-header-icon {
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            color: #fff;
        }

        .btn-gradient {
            background: linear-gradient(135deg, #5a4fcf, #3a6bb4, #1ca8a0);
            color: white;
            border: none;
        }

        .btn-gradient:hover {
            filter: brightness(1.1);
        }

        .main-rectangle {
            min-height: 650px;
            display: flex;
            flex-direction: column;
        }

        .header-bar {
            margin-bottom: 1.5rem;
        }

        .top-section {
            margin-bottom: 2.5rem;
        }

        .bottom-section {
            display: flex;
            gap: 2.5rem;
            align-items: flex-start;
        }

        .left-info {
            flex: 2;
            min-width: 0;
            display: flex;
            flex-direction: column;
            gap: 2rem;
        }

        .right-actions {
            flex: 1;
            min-width: 260px;
            display: flex;
            flex-direction: column;
        }

        .card-4grid .card {
            border-radius: 10px;
        }

        @media (max-width: 992px) {
            .main-rectangle {
                min-height: unset;
            }

            .header-bar {
                margin-bottom: 1rem;
            }

            .top-section {
                margin-bottom: 1rem;
            }

            .bottom-section {
                flex-direction: column;
                gap: 1.2rem;
            }

            .left-info,
            .right-actions {
                flex: none;
            }
        }
    </style>
</head>

<body>
    <div class="container py-4">
        <div class="main-rectangle mx-auto" style="max-width: 1280px;">
            <div class="header-bar d-flex justify-content-between align-items-center">
                <div>
                    <h2 class="text-white fw-bold">Zoo Management System</h2>
                    <small class="text-white-50">Hệ thống quản lý sở thú</small>
                </div>
                <div class="text-end">
                    <div class="badge bg-warning text-dark mb-1">
                        <i class="fas fa-user"></i>
                        <span th:text="${name}">Tên nhân viên</span>
                    </div>
                    <div class="badge bg-white text-dark">
                        <i class="fas fa-clock me-1"></i>
                        <span id="liveTime"></span>
                    </div>
                </div>
            </div>
            <div class="top-section">
                <div class="card gradient-card p-4">
                    <div class="row g-3 row-cols-2 row-cols-md-auto">
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-primary mb-2 mx-auto">
                                    <i class="fas fa-paw"></i>
                                </div>
                                <h4 th:text="${dongVatCount}">0</h4>
                                <small class="text-muted">Động vật</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-success mb-2 mx-auto">
                                    <i class="fas fa-ticket-alt"></i>
                                </div>
                                <h4 th:text="${loaiVeCount}">0</h4>
                                <small class="text-muted">Loại vé</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-danger mb-2 mx-auto">
                                    <i class="fas fa-heart"></i>
                                </div>
                                <h4 th:text="${tongNhanVien}">0</h4>
                                <small class="text-muted">Tổng nhân viên</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-secondary mb-2 mx-auto">
                                    <i class="fas fa-house-damage"></i>
                                </div>
                                <h4 th:text="${chuongCount}">0</h4>
                                <small class="text-muted">Chuồng</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-info mb-2 mx-auto">
                                    <i class="fas fa-calendar-alt"></i>
                                </div>
                                <h4 th:text="${lichChoAnCount}">0</h4>
                                <small class="text-muted">Lịch cho ăn</small>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="bottom-section">
                <div class="left-info">
                    <div class="card gradient-card p-4 card-4grid">
                        <div class="row g-3">
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div class="card-header bg-light fw-bold">
                                        <i class="fas fa-user-tie me-2"></i>Thông tin nhân viên
                                    </div>
                                    <ul class="list-group list-group-flush" th:each="nv : ${danhSachNhanVien}">
                                        <li class="list-group-item">
                                            <div class="fw-semibold" th:text="${nv.fullname}">Tên</div>
                                            <div class="small text-muted">Mã nhân viên: <span
                                                    th:text="${nv.id}">1</span></div>
                                            <div class="small">Tài khoản: <span th:text="${nv.username}">admin</span>
                                            </div>
                                            <div class="small">Ngày vào làm: <span
                                                    th:text="${nv.datework}">2025-08-05</span></div>
                                            <div class="small">SĐT: <span th:text="${nv.phone}">0341234567</span></div>
                                            <div class="small">Chuồng: <span th:text="${nv.chuong}">Capybara</span>
                                            </div>
                                            <div class="small">Cấp bậc: <span th:text="${nv.role}">admin</span></div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div
                                        class="card-header bg-light fw-bold d-flex justify-content-between align-items-center">
                                        <span><i class="fas fa-paw me-2"></i>Thông tin động vật</span>
                                        <form method="get" action="/greeting" class="d-inline">
                                            <select name="dongVatId" class="form-select form-select-sm d-inline w-auto"
                                                onchange="this.form.submit()"
                                                th:if="${not #lists.isEmpty(danhSachDongVat)}">
                                                <option th:each="dv : ${danhSachDongVat}" th:value="${dv.id}"
                                                    th:text="${dv.ten}" th:selected="${dv.id == dongVat?.id}">Động vật
                                                </option>
                                            </select>
                                        </form>
                                    </div>
                                    <ul class="list-group list-group-flush" th:if="${dongVat != null}">
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Tên</span><span th:text="${dongVat.ten}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Tuổi</span><span th:text="${dongVat.tuoi + ' tuổi'}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Loài</span><span th:text="${dongVat.loai}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Sức khỏe</span>
                                            <span th:text="${dongVat.sucKhoe}"
                                                th:classappend="${dongVat.sucKhoe.toLowerCase().contains('khỏe') ? 'text-success' : 'text-danger'}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Khu vực</span><span
                                                th:text="${dongVat.khuVuc != null ? dongVat.khuVuc : 'Không xác định'}">...</span>
                                        </li>
                                    </ul>
                                    <div class="p-2 text-danger fw-bold" th:if="${dongVat == null}">
                                        Không có dữ liệu động vật trong hệ thống!
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div class="card-header bg-light fw-bold">
                                        <i class="fas fa-ticket-alt me-2"></i>Thông tin giá vé
                                    </div>
                                    <ul class="list-group list-group-flush"
                                        th:if="${not #lists.isEmpty(danhSachGiaVe)}">
                                        <li class="list-group-item" th:each="ve : ${danhSachGiaVe}">
                                            <div class="fw-semibold mb-1" th:text="${ve.loaiVe}">Vé Loại</div>
                                            <div class="d-flex justify-content-between small">
                                                <span>Giá gốc:</span>
                                                <span
                                                    th:text="${#numbers.formatDecimal(ve.giaCoBan, 0, 'POINT', 0, 'COMMA')} + ' VNĐ'">0
                                                    VNĐ</span>
                                            </div>
                                            <div class="d-flex justify-content-between small"
                                                th:if="${ve.phanTramGiamGia > 0}">
                                                <span>Giảm giá:</span>
                                                <span class="text-warning"
                                                    th:text="${ve.phanTramGiamGia + '%'}">0%</span>
                                            </div>
                                            <div class="d-flex justify-content-between small"
                                                th:if="${ve.phanTramGiamGia > 0}">
                                                <span>Giá sau giảm:</span>
                                                <span class="text-success"
                                                    th:text="${#numbers.formatDecimal(ve.giaCoBan * (1 - ve.phanTramGiamGia / 100), 0, 'POINT', 0, 'COMMA')} + ' VNĐ'">0
                                                    VNĐ</span>
                                            </div>
                                            <hr class="my-2">
                                        </li>
                                    </ul>
                                    <div class="p-2 text-danger" th:if="${#lists.isEmpty(danhSachGiaVe)}">
                                        Không có vé nào trong hệ thống.
                                    </div>
                                </div>
                            </div>
                            <!-- Phải dưới: Chuồng -->
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div
                                        class="card-header bg-light fw-bold d-flex justify-content-between align-items-center">
                                        <span><i class="fas fa-house-damage me-2"></i>Thông tin chuồng</span>
                                        <form method="get" action="/greeting" class="d-inline">
                                            <select name="chuongId" class="form-select form-select-sm d-inline w-auto"
                                                onchange="this.form.submit()"
                                                th:if="${not #lists.isEmpty(danhSachChuong)}">
                                                <option th:each="c : ${danhSachChuong}" th:value="${c.maChuong}"
                                                    th:text="${c.tenKhuVuc + ' (' + c.maChuong + ')'}"
                                                    th:selected="${c.maChuong == chuong?.maChuong}">Chuồng</option>
                                            </select>
                                        </form>
                                    </div>
                                    <ul class="list-group list-group-flush" th:if="${chuong != null}">
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Mã chuồng</span><span th:text="${chuong.maChuong}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Khu vực</span><span th:text="${chuong.tenKhuVuc}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Sức chứa</span><span th:text="${chuong.sucChuaToiDa}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Số lượng hiện tại</span><span
                                                th:text="${chuong.soLuongHienTai}">...</span>
                                        </li>
                                    </ul>
                                    <div class="p-2 text-danger fw-bold" th:if="${chuong == null}">
                                        Không có dữ liệu chuồng trong hệ thống!
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- BÊN PHẢI: Thao tác nhanh -->
                <div class="right-actions">
                    <div class="card gradient-card p-4 d-flex flex-column align-items-stretch">
                        <h5 class="text-center fw-bold mb-3"><i class="fas fa-rocket me-2"></i>Thao tác nhanh</h5>
                        <div class="d-flex flex-column gap-3">
                            <a href="/nhanvien" class="btn btn-gradient w-100"><i class="fas fa-user-tie me-1"></i>Quản
                                lý Nhân viên</a>
                            <a href="/chuong" class="btn btn-gradient w-100"><i class="fas fa-house-damage me-1"></i>
                                Quản lý Chuồng</a>
                            <a href="/dongvat" class="btn btn-gradient w-100"><i class="fas fa-paw me-1"></i> Quản lý
                                Động Vật</a>
                            <a href="/giave" class="btn btn-gradient w-100"><i class="fas fa-ticket-alt me-1"></i> Quản
                                lý Giá Vé</a>
                            <a href="/lichchoan" class="btn btn-gradient w-100"><i
                                    class="fas fa-calendar-check me-1"></i>Quản lý Lịch Cho Ăn</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function updateTime() {
            const now = new Date();
            const options = { year: "numeric", month: "2-digit", day: "2-digit", hour: "2-digit", minute: "2-digit", second: "2-digit", hour12: false };
            const formatter = new Intl.DateTimeFormat("vi-VN", options);
            document.getElementById("liveTime").textContent = formatter.format(now).replace(",", "");
        }
        updateTime();
        setInterval(updateTime, 1000);
    </script>
</body>

</html>
<!DOCTYPE html>
<html lang="vi" xmlns:th="http://www.thymeleaf.org">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Zoo Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;800&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            color: #333;
        }

        .gradient-card {
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(10px);
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .gradient-card:hover {
            transform: scale(1.02);
        }

        .card-header-icon {
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            color: #fff;
        }

        .btn-gradient {
            background: linear-gradient(135deg, #5a4fcf, #3a6bb4, #1ca8a0);
            color: white;
            border: none;
        }

        .btn-gradient:hover {
            filter: brightness(1.1);
        }

        .main-rectangle {
            min-height: 650px;
            display: flex;
            flex-direction: column;
        }

        .header-bar {
            margin-bottom: 1.5rem;
        }

        .top-section {
            margin-bottom: 2.5rem;
        }

        .bottom-section {
            display: flex;
            gap: 2.5rem;
            align-items: flex-start;
        }

        .left-info {
            flex: 2;
            min-width: 0;
            display: flex;
            flex-direction: column;
            gap: 2rem;
        }

        .right-actions {
            flex: 1;
            min-width: 260px;
            display: flex;
            flex-direction: column;
        }

        .card-4grid .card {
            border-radius: 10px;
        }

        @media (max-width: 992px) {
            .main-rectangle {
                min-height: unset;
            }

            .header-bar {
                margin-bottom: 1rem;
            }

            .top-section {
                margin-bottom: 1rem;
            }

            .bottom-section {
                flex-direction: column;
                gap: 1.2rem;
            }

            .left-info,
            .right-actions {
                flex: none;
            }
        }
    </style>
</head>

<body>
    <div class="container py-4">
        <div class="main-rectangle mx-auto" style="max-width: 1280px;">
            <div class="header-bar d-flex justify-content-between align-items-center">
                <div>
                    <h2 class="text-white fw-bold">Zoo Management System</h2>
                    <small class="text-white-50">Hệ thống quản lý sở thú</small>
                </div>
                <div class="text-end">
                    <div class="badge bg-warning text-dark mb-1">
                        <i class="fas fa-user"></i>
                        <span th:text="${name}">Tên nhân viên</span>
                    </div>
                    <div class="badge bg-white text-dark">
                        <i class="fas fa-clock me-1"></i>
                        <span id="liveTime"></span>
                    </div>
                </div>
            </div>
            <div class="top-section">
                <div class="card gradient-card p-4">
                    <div class="row g-3 row-cols-2 row-cols-md-auto">
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-primary mb-2 mx-auto">
                                    <i class="fas fa-paw"></i>
                                </div>
                                <h4 th:text="${dongVatCount}">0</h4>
                                <small class="text-muted">Động vật</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-success mb-2 mx-auto">
                                    <i class="fas fa-ticket-alt"></i>
                                </div>
                                <h4 th:text="${loaiVeCount}">0</h4>
                                <small class="text-muted">Loại vé</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-danger mb-2 mx-auto">
                                    <i class="fas fa-heart"></i>
                                </div>
                                <h4 th:text="${tongNhanVien}">0</h4>
                                <small class="text-muted">Tổng nhân viên</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-secondary mb-2 mx-auto">
                                    <i class="fas fa-house-damage"></i>
                                </div>
                                <h4 th:text="${chuongCount}">0</h4>
                                <small class="text-muted">Chuồng</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-info mb-2 mx-auto">
                                    <i class="fas fa-calendar-alt"></i>
                                </div>
                                <h4 th:text="${lichChoAnCount}">0</h4>
                                <small class="text-muted">Lịch cho ăn</small>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="bottom-section">
                <div class="left-info">
                    <div class="card gradient-card p-4 card-4grid">
                        <div class="row g-3">
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div class="card-header bg-light fw-bold">
                                        <i class="fas fa-user-tie me-2"></i>Thông tin nhân viên
                                    </div>
                                    <ul class="list-group list-group-flush" th:each="nv : ${danhSachNhanVien}">
                                        <li class="list-group-item">
                                            <div class="fw-semibold" th:text="${nv.fullname}">Tên</div>
                                            <div class="small text-muted">Mã nhân viên: <span
                                                    th:text="${nv.id}">1</span></div>
                                            <div class="small">Tài khoản: <span th:text="${nv.username}">admin</span>
                                            </div>
                                            <div class="small">Ngày vào làm: <span
                                                    th:text="${nv.datework}">2025-08-05</span></div>
                                            <div class="small">SĐT: <span th:text="${nv.phone}">0341234567</span></div>
                                            <div class="small">Chuồng: <span th:text="${nv.chuong}">Capybara</span>
                                            </div>
                                            <div class="small">Cấp bậc: <span th:text="${nv.role}">admin</span></div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div
                                        class="card-header bg-light fw-bold d-flex justify-content-between align-items-center">
                                        <span><i class="fas fa-paw me-2"></i>Thông tin động vật</span>
                                        <form method="get" action="/greeting" class="d-inline">
                                            <select name="dongVatId" class="form-select form-select-sm d-inline w-auto"
                                                onchange="this.form.submit()"
                                                th:if="${not #lists.isEmpty(danhSachDongVat)}">
                                                <option th:each="dv : ${danhSachDongVat}" th:value="${dv.id}"
                                                    th:text="${dv.ten}" th:selected="${dv.id == dongVat?.id}">Động vật
                                                </option>
                                            </select>
                                        </form>
                                    </div>
                                    <ul class="list-group list-group-flush" th:if="${dongVat != null}">
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Tên</span><span th:text="${dongVat.ten}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Tuổi</span><span th:text="${dongVat.tuoi + ' tuổi'}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Loài</span><span th:text="${dongVat.loai}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Sức khỏe</span>
                                            <span th:text="${dongVat.sucKhoe}"
                                                th:classappend="${dongVat.sucKhoe.toLowerCase().contains('khỏe') ? 'text-success' : 'text-danger'}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Khu vực</span><span
                                                th:text="${dongVat.khuVuc != null ? dongVat.khuVuc : 'Không xác định'}">...</span>
                                        </li>
                                    </ul>
                                    <div class="p-2 text-danger fw-bold" th:if="${dongVat == null}">
                                        Không có dữ liệu động vật trong hệ thống!
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div class="card-header bg-light fw-bold">
                                        <i class="fas fa-ticket-alt me-2"></i>Thông tin giá vé
                                    </div>
                                    <ul class="list-group list-group-flush"
                                        th:if="${not #lists.isEmpty(danhSachGiaVe)}">
                                        <li class="list-group-item" th:each="ve : ${danhSachGiaVe}">
                                            <div class="fw-semibold mb-1" th:text="${ve.loaiVe}">Vé Loại</div>
                                            <div class="d-flex justify-content-between small">
                                                <span>Giá gốc:</span>
                                                <span
                                                    th:text="${#numbers.formatDecimal(ve.giaCoBan, 0, 'POINT', 0, 'COMMA')} + ' VNĐ'">0
                                                    VNĐ</span>
                                            </div>
                                            <div class="d-flex justify-content-between small"
                                                th:if="${ve.phanTramGiamGia > 0}">
                                                <span>Giảm giá:</span>
                                                <span class="text-warning"
                                                    th:text="${ve.phanTramGiamGia + '%'}">0%</span>
                                            </div>
                                            <div class="d-flex justify-content-between small"
                                                th:if="${ve.phanTramGiamGia > 0}">
                                                <span>Giá sau giảm:</span>
                                                <span class="text-success"
                                                    th:text="${#numbers.formatDecimal(ve.giaCoBan * (1 - ve.phanTramGiamGia / 100), 0, 'POINT', 0, 'COMMA')} + ' VNĐ'">0
                                                    VNĐ</span>
                                            </div>
                                            <hr class="my-2">
                                        </li>
                                    </ul>
                                    <div class="p-2 text-danger" th:if="${#lists.isEmpty(danhSachGiaVe)}">
                                        Không có vé nào trong hệ thống.
                                    </div>
                                </div>
                            </div>
                            <!-- Phải dưới: Chuồng -->
                            <div class="col-12 col-md-6">
                                <div class="card h-100">
                                    <div
                                        class="card-header bg-light fw-bold d-flex justify-content-between align-items-center">
                                        <span><i class="fas fa-house-damage me-2"></i>Thông tin chuồng</span>
                                        <form method="get" action="/greeting" class="d-inline">
                                            <select name="chuongId" class="form-select form-select-sm d-inline w-auto"
                                                onchange="this.form.submit()"
                                                th:if="${not #lists.isEmpty(danhSachChuong)}">
                                                <option th:each="c : ${danhSachChuong}" th:value="${c.maChuong}"
                                                    th:text="${c.tenKhuVuc + ' (' + c.maChuong + ')'}"
                                                    th:selected="${c.maChuong == chuong?.maChuong}">Chuồng</option>
                                            </select>
                                        </form>
                                    </div>
                                    <ul class="list-group list-group-flush" th:if="${chuong != null}">
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Mã chuồng</span><span th:text="${chuong.maChuong}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Khu vực</span><span th:text="${chuong.tenKhuVuc}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Sức chứa</span><span th:text="${chuong.sucChuaToiDa}">...</span>
                                        </li>
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>Số lượng hiện tại</span><span
                                                th:text="${chuong.soLuongHienTai}">...</span>
                                        </li>
                                    </ul>
                                    <div class="p-2 text-danger fw-bold" th:if="${chuong == null}">
                                        Không có dữ liệu chuồng trong hệ thống!
                                    </div>
                                </div>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Zoo Management System - Dashboard</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            --purple-blue-gradient: linear-gradient(135deg, #6a5acd 0%, #4682b4 50%, #20b2aa 100%);
            --card-gradient: linear-gradient(135deg, rgba(255,255,255,0.95) 0%, rgba(255,255,255,0.85) 100%);
            --primary-color: #6366f1;
            --primary-dark: #4f46e5;
            --secondary-color: #10b981;
            --accent-color: #f59e0b;
            --danger-color: #ef4444;
            --purple-color: #8b5cf6;
            --teal-color: #14b8a6;
            --text-primary: #1f2937;
            --text-secondary: #6b7280;
            --border-color: rgba(255,255,255,0.2);
            --shadow-glow: 0 0 20px rgba(139, 92, 246, 0.3);
            --shadow-intense: 0 0 40px rgba(139, 92, 246, 0.5);
            --special-glow: 0 0 30px rgba(106, 90, 205, 0.6), 0 0 60px rgba(70, 130, 180, 0.4), 0 0 90px rgba(32, 178, 170, 0.3);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
            background: var(--purple-blue-gradient);
            min-height: 100vh;
            line-height: 1.5;
            color: var(--text-primary);
            overflow-x: hidden;
            position: relative;
        }

        /* Enhanced background with animated particles */
        body::before {
            content: '';
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: 
                radial-gradient(circle at 20% 80%, rgba(138, 43, 226, 0.4) 0%, transparent 60%),
                radial-gradient(circle at 80% 20%, rgba(30, 144, 255, 0.4) 0%, transparent 60%),
                radial-gradient(circle at 40% 40%, rgba(0, 206, 209, 0.3) 0%, transparent 60%);
            pointer-events: none;
            z-index: -1;
            animation: backgroundFloat 20s ease-in-out infinite;
        }

        @keyframes backgroundFloat {
            0%, 100% { transform: translateY(0px) scale(1); }
            50% { transform: translateY(-20px) scale(1.05); }
        }

        .main-container {
            min-height: 100vh;
            display: grid;
            grid-template-rows: auto 1fr;
        }

        /* Compact Header */
        .header {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(25px);
            border-bottom: 2px solid var(--border-color);
            padding: 0.8rem 1.5rem;
            position: sticky;
            top: 0;
            z-index: 100;
            border-radius: 0 0 15px 15px;
            box-shadow: 0 8px 32px rgba(139, 92, 246, 0.2);
            position: relative;
            overflow: hidden;
        }

        .header::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.1), transparent);
            animation: shimmer 3s ease-in-out infinite;
        }

        @keyframes shimmer {
            0% { left: -100%; }
            100% { left: 100%; }
        }

        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            align-items: center;
            justify-content: space-between;
            flex-wrap: wrap;
            gap: 0.5rem;
        }

        .logo-section {
            display: flex;
            align-items: center;
            gap: 0.6rem;
        }

        .logo {
            width: 35px;
            height: 35px;
            background: var(--purple-blue-gradient);
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.1rem;
            box-shadow: var(--shadow-glow);
            transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
            position: relative;
            overflow: hidden;
        }

        .logo::after {
            content: '';
            position: absolute;
            inset: -2px;
            background: var(--purple-blue-gradient);
            border-radius: 12px;
            z-index: -1;
            filter: blur(8px);
            opacity: 0.7;
        }

        .logo:hover {
            transform: scale(1.02);
            box-shadow: var(--shadow-intense);
        }

        .brand-info h1 {
            font-size: 1.1rem;
            font-weight: 700;
            color: white;
            margin-bottom: 0.1rem;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
        }

        .brand-info .subtitle {
            font-size: 0.7rem;
            color: rgba(255, 255, 255, 0.8);
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3);
        }

        .user-section {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        /* Enhanced welcome badge với ô "Hello" cực kỳ nổi bật và gap nhỏ */
        .welcome-badge {
            background: rgba(147, 112, 219, 0.3);
            backdrop-filter: blur(10px);
            color: white;
            padding: 0.4rem 0.8rem;
            border-radius: 20px;
            font-size: 0.7rem;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 0.1rem; /* Giảm từ 0.3rem xuống 0.1rem */
            border: 2px solid rgba(147, 112, 219, 0.6);
            box-shadow: 0 0 15px rgba(147, 112, 219, 0.4);
            transition: all 0.3s ease;
            position: relative;
        }

        .welcome-badge .greeting-text {
            background: linear-gradient(135deg, #ffffff 0%, #ffd700 50%, #ffff00 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            font-weight: 900;
            font-size: 0.85rem;
            text-shadow: 0 0 10px rgba(255, 215, 0, 0.8);
            padding: 2px 6px;
            border-radius: 6px;
            position: relative;
            animation: textGlow 2s ease-in-out infinite alternate;
        }

        @keyframes textGlow {
            0% {
                text-shadow: 0 0 10px rgba(255, 215, 0, 0.8);
                filter: brightness(1);
            }
            100% {
                text-shadow: 0 0 20px rgba(255, 215, 0, 1), 0 0 30px rgba(255, 255, 255, 0.5);
                filter: brightness(1.2);
            }
        }

        .welcome-badge .greeting-text::before {
            content: '';
            position: absolute;
            inset: -2px;
            background: linear-gradient(135deg, rgba(255, 215, 0, 0.3), rgba(255, 255, 255, 0.2));
            border-radius: 8px;
            z-index: -1;
            filter: blur(4px);
        }

        .welcome-badge:hover {
            transform: scale(1.02);
            background: rgba(147, 112, 219, 0.4);
        }

        .time-display {
            background: rgba(255, 255, 255, 0.15);
            backdrop-filter: blur(10px);
            border: 1px solid var(--border-color);
            padding: 0.4rem 0.8rem;
            border-radius: 10px;
            font-size: 0.7rem;
            color: white;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.3);
            transition: all 0.3s ease;
        }

        .time-display:hover {
            transform: scale(1.02);
            background: rgba(255, 255, 255, 0.2);
        }

        /* Compact Main Content */
        .content {
            padding: 1rem;
        }

        .dashboard {
            max-width: 1200px;
            margin: 0 auto;
            display: grid;
            gap: 1rem;
        }

        /* Ultra Compact Stats Grid */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 0.8rem;
        }

        .stat-card {
            background: var(--card-gradient);
            backdrop-filter: blur(20px);
            border-radius: 12px;
            padding: 0.8rem;
            box-shadow: 0 4px 15px rgba(139, 92, 246, 0.2);
            border: 1px solid var(--border-color);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            position: relative;
            overflow: hidden;
        }

        .stat-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 3px;
            background: var(--gradient);
            transition: all 0.3s ease;
        }

        .stat-card::after {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
            transition: left 0.6s;
        }

        .stat-card:hover {
            transform: scale(1.02);
            box-shadow: var(--shadow-glow);
        }

        .stat-card:hover::before {
            height: 4px;
            box-shadow: 0 0 10px currentColor;
        }

        .stat-card:hover::after {
            left: 100%;
        }

        .stat-card.primary::before { background: linear-gradient(90deg, #8b5cf6, #6366f1); }
        .stat-card.success::before { background: linear-gradient(90deg, #10b981, #14b8a6); }
        .stat-card.warning::before { background: linear-gradient(90deg, #f59e0b, #f97316); }
        .stat-card.danger::before { background: linear-gradient(90deg, #ef4444, #dc2626); }

        .stat-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 0.6rem;
        }

        .stat-icon {
            width: 30px;
            height: 30px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 0.9rem;
            color: white;
            transition: all 0.3s ease;
            position: relative;
        }

        .stat-icon::before {
            content: '';
            position: absolute;
            inset: -2px;
            background: inherit;
            border-radius: 10px;
            filter: blur(8px);
            z-index: -1;
            opacity: 0.6;
        }

        .stat-card:hover .stat-icon {
            transform: scale(1.02);
        }

        .stat-icon.primary { background: linear-gradient(135deg, #8b5cf6, #6366f1); }
        .stat-icon.success { background: linear-gradient(135deg, #10b981, #14b8a6); }
        .stat-icon.warning { background: linear-gradient(135deg, #f59e0b, #f97316); }
        .stat-icon.danger { background: linear-gradient(135deg, #ef4444, #dc2626); }

        .stat-trend {
            font-size: 0.6rem;
            padding: 0.2rem 0.4rem;
            border-radius: 10px;
            font-weight: 600;
            background: rgba(16, 185, 129, 0.1);
            color: var(--secondary-color);
            border: 1px solid rgba(16, 185, 129, 0.2);
            transition: all 0.3s ease;
        }

        .stat-trend:hover {
            transform: scale(1.02);
            background: rgba(16, 185, 129, 0.2);
        }

        .stat-number {
            font-size: 1.4rem;
            font-weight: 700;
            color: var(--text-primary);
            margin-bottom: 0.2rem;
            transition: all 0.3s ease;
        }

        .stat-card:hover .stat-number {
            transform: scale(1.02);
        }

        .stat-label {
            font-size: 0.7rem;
            color: var(--text-secondary);
            font-weight: 500;
        }

        /* Ultra Compact Info Cards */
        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 1rem;
        }

        .info-card {
            background: var(--card-gradient);
            backdrop-filter: blur(20px);
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(139, 92, 246, 0.2);
            border: 1px solid var(--border-color);
            overflow: hidden;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            position: relative;
        }

        .info-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.15), transparent);
            transition: left 0.8s;
        }

        .info-card::after {
            content: '';
            position: absolute;
            inset: -1px;
            background: var(--purple-blue-gradient);
            border-radius: 16px;
            z-index: -1;
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        .info-card:hover {
            transform: scale(1.02);
            box-shadow: var(--shadow-glow);
        }

        .info-card:hover::before {
            left: 100%;
        }

        .info-card:hover::after {
            opacity: 0.3;
            filter: blur(8px);
        }

        .card-header {
            padding: 0.8rem 1rem;
            background: linear-gradient(135deg, rgba(248, 250, 252, 0.8), rgba(226, 232, 240, 0.8));
            backdrop-filter: blur(10px);
            border-bottom: 1px solid var(--border-color);
            display: flex;
            align-items: center;
            gap: 0.6rem;
        }

        .card-icon {
            width: 28px;
            height: 28px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 0.9rem;
            color: white;
            transition: all 0.3s ease;
            position: relative;
        }

        .card-icon::before {
            content: '';
            position: absolute;
            inset: -2px;
            background: inherit;
            border-radius: 10px;
            filter: blur(6px);
            z-index: -1;
            opacity: 0.7;
        }

        .info-card:hover .card-icon {
            transform: scale(1.02);
        }

        .card-title {
            font-size: 0.9rem;
            font-weight: 700;
            color: var(--text-primary);
        }

        .card-body {
            padding: 1rem;
        }

        .info-list {
            list-style: none;
            display: flex;
            flex-direction: column;
            gap: 0.6rem;
        }

        .info-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0.6rem;
            background: rgba(248, 250, 252, 0.7);
            backdrop-filter: blur(10px);
            border-radius: 8px;
            border: 1px solid var(--border-color);
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            position: relative;
            overflow: hidden;
        }

        .info-item::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(139, 92, 246, 0.1), transparent);
            transition: left 0.4s;
        }

        .info-item:hover {
            background: rgba(241, 245, 249, 0.9);
            border-color: var(--purple-color);
            transform: scale(1.02);
        }

        .info-item:hover::before {
            left: 100%;
        }

        .info-label {
            display: flex;
            align-items: center;
            gap: 0.4rem;
            font-weight: 600;
            color: var(--text-secondary);
            font-size: 0.7rem;
        }

        .info-label i {
            transition: all 0.3s ease;
            font-size: 0.8rem;
        }

        .info-item:hover .info-label i {
            transform: translateX(-2px) scale(1.1);
            color: var(--purple-color);
        }

        .info-value {
            font-weight: 700;
            color: var(--text-primary);
            font-size: 0.7rem;
            transition: all 0.3s ease;
        }

        .info-item:hover .info-value {
            transform: scale(1.02);
        }

        .info-value.success { color: var(--secondary-color); }
        .info-value.warning { color: var(--accent-color); }
        .info-value.danger { color: var(--danger-color); }

        .badge {
            display: inline-flex;
            align-items: center;
            padding: 0.2rem 0.5rem;
            border-radius: 20px;
            font-size: 0.6rem;
            font-weight: 600;
            margin-left: 0.4rem;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .badge::before {
            content: '';
            position: absolute;
            inset: 0;
            background: inherit;
            filter: blur(6px);
            opacity: 0.5;
            z-index: -1;
        }

        .badge.new {
            background: linear-gradient(135deg, #ff6b6b, #ee5a24);
            color: white;
            box-shadow: 0 2px 8px rgba(255, 107, 107, 0.3);
        }

        .badge.hot {
            background: linear-gradient(135deg, #fd79a8, #e84393);
            color: white;
            box-shadow: 0 2px 8px rgba(253, 121, 168, 0.3);
        }

        .badge:hover {
            transform: scale(1.02);
        }

        /* Compact Calculation Result */
        .calculation-result {
            background: linear-gradient(135deg, rgba(254, 243, 199, 0.9), rgba(251, 191, 36, 0.9));
            backdrop-filter: blur(10px);
            border: 2px solid rgba(245, 158, 11, 0.3);
            border-radius: 10px;
            padding: 0.8rem;
            margin-top: 0.8rem;
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .calculation-result::before {
            content: '';
            position: absolute;
            inset: -2px;
            background: linear-gradient(45deg, #f59e0b, #d97706, #f59e0b);
            border-radius: 12px;
            z-index: -1;
            background-size: 200% 200%;
            animation: gradientShift 3s ease infinite;
        }

        @keyframes gradientShift {
            0%, 100% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
        }

        .calculation-result:hover {
            transform: scale(1.02);
            box-shadow: 0 6px 20px rgba(245, 158, 11, 0.3);
        }

        .result-label {
            font-size: 0.7rem;
            font-weight: 600;
            color: #92400e;
            margin-bottom: 0.4rem;
        }

        .result-value {
            font-size: 0.9rem;
            font-weight: 700;
            color: #b45309;
        }

        /* Enhanced Action Buttons với hiệu ứng đặc biệt */
        .actions-section {
            background: var(--card-gradient);
            backdrop-filter: blur(20px);
            border-radius: 15px;
            padding: 1.5rem;
            box-shadow: 0 4px 15px rgba(139, 92, 246, 0.2);
            border: 1px solid var(--border-color);
            position: relative;
            overflow: hidden;
        }

        .actions-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 3px;
            background: var(--purple-blue-gradient);
            background-size: 200% 100%;
            animation: rainbow 4s linear infinite;
        }

        @keyframes rainbow {
            0% { background-position: 0% 50%; }
            100% { background-position: 200% 50%; }
        }

        .section-title {
            font-size: 1rem;
            font-weight: 700;
            color: var(--text-primary);
            margin-bottom: 1rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            text-align: center;
            justify-content: center;
        }

        .section-title i {
            font-size: 1.1rem;
            background: var(--purple-blue-gradient);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .actions-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
            gap: 0.8rem;
        }

        .action-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.4rem;
            padding: 0.8rem 1rem;
            border-radius: 12px;
            text-decoration: none;
            font-weight: 600;
            font-size: 0.75rem;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            border: 2px solid transparent;
            text-align: center;
            position: relative;
            overflow: hidden;
            z-index: 1;
        }

        .action-btn::before {
            content: '';
            position: absolute;
            inset: 0;
            background: inherit;
            z-index: -2;
            transition: all 0.3s ease;
        }

        .action-btn::after {
            content: '';
            position: absolute;
            inset: -2px;
            background: inherit;
            filter: blur(10px);
            z-index: -3;
            opacity: 0;
            transition: all 0.3s ease;
        }

        .action-btn:hover {
            transform: scale(1.02);
            box-shadow: var(--shadow-glow);
        }

        .action-btn:hover::after {
            opacity: 0.6;
        }

        /* Special Enhanced Buttons cho Quản lý động vật và Quản lý giá vé */
        .action-btn.primary, 
        .action-btn.success {
            background: var(--purple-blue-gradient);
            color: white;
            box-shadow: var(--special-glow);
            position: relative;
            overflow: hidden;
        }

        .action-btn.primary::before,
        .action-btn.success::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.3), transparent);
            animation: shimmerSpecial 2s ease-in-out infinite;
        }

        @keyframes shimmerSpecial {
            0% { left: -100%; }
            100% { left: 100%; }
        }

        .action-btn.primary:hover,
        .action-btn.success:hover {
            transform: scale(1.02);
            box-shadow: var(--special-glow), 0 0 50px rgba(106, 90, 205, 0.8);
            background: linear-gradient(135deg, #5a4fcf 0%, #3a6bb4 50%, #1ca8a0 100%);
        }

        .action-btn.primary:hover::after,
        .action-btn.success:hover::after {
            opacity: 0.8;
            filter: blur(15px);
        }

        .action-btn.outline {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            color: var(--purple-color);
            border: 2px solid var(--purple-color);
            position: relative;
        }

        .action-btn.outline::before {
            content: '';
            position: absolute;
            inset: 0;
            background: var(--purple-blue-gradient);
            border-radius: inherit;
            opacity: 0;
            transition: opacity 0.3s ease;
            z-index: -1;
        }

        .action-btn.outline:hover {
            color: white;
            border-color: transparent;
        }

        .action-btn.outline:hover::before {
            opacity: 1;
        }

        .action-btn i {
            transition: all 0.3s ease;
        }

        .action-btn:hover i {
            transform: translateX(-2px) scale(1.1);
        }

        .action-btn.loading {
            pointer-events: none;
            opacity: 0.7;
        }

        .action-btn.loading i {
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            from { transform: rotate(0deg); }
            to { transform: rotate(360deg); }
        }

        /* Enhanced Status Indicator với màu xanh lá dễ nhìn */
        .status-indicator {
            display: inline-flex;
            align-items: center;
            gap: 0.4rem;
            padding: 0.4rem 0.8rem;
            background: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(10px);
            border: 2px solid rgba(34, 197, 94, 0.6);
            border-radius: 20px;
            color: #16a34a;
            font-size: 0.7rem;
            font-weight: 700;
            transition: all 0.3s ease;
            box-shadow: 0 0 15px rgba(34, 197, 94, 0.4);
        }

        .status-indicator:hover {
            transform: scale(1.02);
            background: rgba(255, 255, 255, 1);
            box-shadow: 0 0 20px rgba(34, 197, 94, 0.6);
            color: #15803d;
            border-color: rgba(34, 197, 94, 0.8);
        }

        .status-dot {
            width: 8px;
            height: 8px;
            background: #22c55e;
            border-radius: 50%;
            position: relative;
            box-shadow: 0 0 8px rgba(34, 197, 94, 0.8);
        }

        .status-dot::before {
            content: '';
            position: absolute;
            inset: -4px;
            border: 2px solid #22c55e;
            border-radius: 50%;
            animation: pulse-ring 2s ease-out infinite;
        }

        @keyframes pulse-ring {
            0% {
                transform: scale(0.8);
                opacity: 1;
            }
            100% {
                transform: scale(2.5);
                opacity: 0;
            }
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .header-content {
                flex-direction: column;
                align-items: flex-start;
                gap: 0.5rem;
            }

            .user-section {
                flex-wrap: wrap;
                width: 100%;
                justify-content: space-between;
            }

            .content {
                padding: 0.8rem;
            }

            .stats-grid {
                grid-template-columns: repeat(2, 1fr);
                gap: 0.6rem;
            }

            .info-grid {
                grid-template-columns: 1fr;
                gap: 0.8rem;
            }

            .actions-grid {
                grid-template-columns: repeat(2, 1fr);
                gap: 0.6rem;
            }

            .stat-card, .info-card {
                padding: 0.6rem;
            }

            .card-header {
                padding: 0.6rem 0.8rem;
            }

            .card-body {
                padding: 0.8rem;
            }
        }

        /* Enhanced Animations */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes slideInLeft {
            from {
                opacity: 0;
                transform: translateX(-20px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }

        @keyframes slideInRight {
            from {
                opacity: 0;
                transform: translateX(20px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }

        .animate-in {
            animation: fadeInUp 0.6s ease-out forwards;
        }

        .animate-slide-left {
            animation: slideInLeft 0.6s ease-out forwards;
        }

        .animate-slide-right {
            animation: slideInRight 0.6s ease-out forwards;
        }

        .animate-delay-1 { animation-delay: 0.1s; }
        .animate-delay-2 { animation-delay: 0.2s; }
        .animate-delay-3 { animation-delay: 0.3s; }
        .animate-delay-4 { animation-delay: 0.4s; }

        .scroll-reveal {
            opacity: 0;
            transform: translateY(20px);
            transition: all 0.6s cubic-bezier(0.4, 0, 0.2, 1);
        }

        .scroll-reveal.revealed {
            opacity: 1;
            transform: translateY(0);
        }
    </style>
</head>

<body>
    <div class="main-container">
        <!-- Compact Header -->
        <header class="header">
            <div class="header-content">
                <div class="logo-section">
                    <div class="logo">
                        <i class="fas fa-leaf"></i>
                    </div>
                    <div class="brand-info">
                        <h1>Zoo Management System</h1>
                        <div class="subtitle">Hệ thống quản lý sở thú</div>
                    </div>
                </div>
                
                <div class="user-section">
                    <div class="welcome-badge">
                        <i class="fas fa-user"></i>
                        <span class="greeting-text">Hello</span><span class="greeting-text" th:text="${name}">Xuân Trường</span>
                    </div>
                    <div class="time-display">
                        <i class="fas fa-clock"></i>
                        <span th:text="${currentTime}">01/08/2025 21:00:00</span>
                    </div>
                    <div class="status-indicator">
                        <div class="status-dot"></div>
                        <span>Hoạt động</span>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="content">
            <div class="dashboard">
                <!-- Ultra Compact Statistics Cards -->
                <section class="stats-grid">
                    <div class="stat-card primary animate-in scroll-reveal">
                        <div class="stat-header">
                            <div class="stat-icon primary">
                                <i class="fas fa-paw"></i>
                            </div>
                            <div class="stat-trend">
                                <i class="fas fa-arrow-up"></i> +12%
                            </div>
                        </div>
                        <div class="stat-number" data-target="15">0</div>
                        <div class="stat-label">Động vật</div>
                    </div>

                    <div class="stat-card success animate-in animate-delay-1 scroll-reveal">
                        <div class="stat-header">
                            <div class="stat-icon success">
                                <i class="fas fa-ticket-alt"></i>
                            </div>
                            <div class="stat-trend">
                                <i class="fas fa-arrow-up"></i> +5%
                            </div>
                        </div>
                        <div class="stat-number" data-target="4">0</div>
                        <div class="stat-label">Loại vé</div>
                    </div>

                    <div class="stat-card warning animate-in animate-delay-2 scroll-reveal">
                        <div class="stat-header">
                            <div class="stat-icon warning">
                                <i class="fas fa-users"></i>
                            </div>
                            <div class="stat-trend">
                                <i class="fas fa-arrow-up"></i> +23%
                            </div>
                        </div>
                        <div class="stat-number" data-target="127">0</div>
                        <div class="stat-label">Khách hôm nay</div>
                    </div>

                    <div class="stat-card danger animate-in animate-delay-3 scroll-reveal">
                        <div class="stat-header">
                            <div class="stat-icon danger">
                                <i class="fas fa-heart"></i>
                            </div>
                            <div class="stat-trend">
                                <i class="fas fa-arrow-up"></i> +2%
                            </div>
                        </div>
                        <div class="stat-number" data-target="98">0</div>
                        <div class="stat-label">Hài lòng (%)</div>
                    </div>
                </section>

                <!-- Ultra Compact Information Cards -->
                <section class="info-grid">
                    <!-- Animal Information Card -->
                    <div class="info-card animate-slide-left animate-delay-1 scroll-reveal">
                        <div class="card-header">
                            <div class="card-icon" style="background: linear-gradient(135deg, #8b5cf6, #6366f1);">
                                <i class="fas fa-paw"></i>
                            </div>
                            <div class="card-title">Thông tin động vật</div>
                        </div>
                        <div class="card-body">
                            <ul class="info-list">
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-tag"></i>
                                        Tên
                                    </span>
                                    <span class="info-value" th:text="${dongVat.ten}">Tên động vật</span>
                                    <span class="badge new">Mới</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-birthday-cake"></i>
                                        Tuổi
                                    </span>
                                    <span class="info-value" th:text="${dongVat.tuoi + ' tuổi'}">5 tuổi</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-dna"></i>
                                        Loài
                                    </span>
                                    <span class="info-value" th:text="${dongVat.loai}">Hổ Bengal</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-heart"></i>
                                        Sức khỏe
                                    </span>
                                    <span class="info-value success">Khỏe mạnh</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-map-marker-alt"></i>
                                        Khu vực
                                    </span>
                                    <span class="info-value">Khu A-12</span>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <!-- Ticket Pricing Card -->
                    <div class="info-card animate-slide-right animate-delay-2 scroll-reveal" th:if="${giaVe != null}">
                        <div class="card-header">
                            <div class="card-icon" style="background: linear-gradient(135deg, #10b981, #14b8a6);">
                                <i class="fas fa-ticket-alt"></i>
                            </div>
                            <div class="card-title">Thông tin giá vé</div>
                        </div>
                        <div class="card-body">
                            <ul class="info-list">
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-id-card"></i>
                                        ID
                                    </span>
                                    <span class="info-value" th:text="${giaVe.id}">001</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-tag"></i>
                                        Loại
                                    </span>
                                    <span class="info-value" th:text="${giaVe.loaiVe}">Người lớn</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-dollar-sign"></i>
                                        Giá gốc
                                    </span>
                                    <span class="info-value success">
                                        <span th:text="${#numbers.formatDecimal(giaVe.giaCoBan, 0, 'POINT', 0, 'COMMA')}">100,000</span> VNĐ
                                    </span>
                                </li>
                                <li class="info-item" th:if="${giaVe.phanTramGiamGia != null && giaVe.phanTramGiamGia > 0}">
                                    <span class="info-label">
                                        <i class="fas fa-percent"></i>
                                        Giảm giá
                                    </span>
                                    <span class="info-value warning" th:text="${giaVe.phanTramGiamGia + '%'}">10%</span>
                                    <span class="badge hot">HOT</span>
                                </li>
                                <li class="info-item" th:if="${giaVe.phanTramGiamGia != null && giaVe.phanTramGiamGia > 0}">
                                    <span class="info-label">
                                        <i class="fas fa-money-bill-wave"></i>
                                        Giá cuối
                                    </span>
                                    <span class="info-value success">
                                        <span th:text="${#numbers.formatDecimal(giaVe.apDungKhuyenMai(), 0, 'POINT', 0, 'COMMA')}">90,000</span> VNĐ
                                    </span>
                                </li>
                            </ul>

                            <div class="calculation-result">
                                <div class="result-label">Ví dụ cho 3 vé:</div>
                                <div class="result-value">
                                    <span th:text="${#numbers.formatDecimal(giaVe.apDungKhuyenMai() * 3, 0, 'POINT', 0, 'COMMA')}">270,000</span> VNĐ
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- System Information Card -->
                    <div class="info-card animate-slide-left animate-delay-3 scroll-reveal">
                        <div class="card-header">
                            <div class="card-icon" style="background: linear-gradient(135deg, #f59e0b, #f97316);">
                                <i class="fas fa-server"></i>
                            </div>
                            <div class="card-title">Hệ thống</div>
                        </div>
                        <div class="card-body">
                            <ul class="info-list">
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-code"></i>
                                        Framework
                                    </span>
                                    <span class="info-value">Spring Boot 3.0</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-leaf"></i>
                                        Template
                                    </span>
                                    <span class="info-value">Thymeleaf</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-coffee"></i>
                                        Java
                                    </span>
                                    <span class="info-value">Java 17</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-database"></i>
                                        Storage
                                    </span>
                                    <span class="info-value">In-Memory</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-clock"></i>
                                        Uptime
                                    </span>
                                    <span class="info-value success">2h 15m</span>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <!-- Features Card -->
                    <div class="info-card animate-slide-right animate-delay-4 scroll-reveal">
                        <div class="card-header">
                            <div class="card-icon" style="background: linear-gradient(135deg, #ef4444, #dc2626);">
                                <i class="fas fa-magic"></i>
                            </div>
                            <div class="card-title">Tính năng</div>
                        </div>
                        <div class="card-body">
                            <ul class="info-list">
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-check-circle"></i>
                                        CRUD Động vật
                                    </span>
                                    <span class="info-value success">Hoàn thành</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-check-circle"></i>
                                        CRUD Giá vé
                                    </span>
                                    <span class="info-value success">Hoàn thành</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-shield-alt"></i>
                                        Validation
                                    </span>
                                    <span class="info-value success">Hoàn thành</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-chart-line"></i>
                                        Báo cáo
                                    </span>
                                    <span class="info-value warning">Phát triển</span>
                                </li>
                                <li class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-users-cog"></i>
                                        User Management
                                    </span>
                                    <span class="info-value danger">Chưa có</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </section>

                <!-- Enhanced Actions Section -->
                <section class="actions-section animate-in animate-delay-4 scroll-reveal">
                    <h2 class="section-title">
                        <i class="fas fa-rocket"></i>
                        Thao tác nhanh
                    </h2>
                    <div class="actions-grid">
                        <a href="/dongvat" class="action-btn primary">
                            <i class="fas fa-list"></i>
                            Quản lý động vật
                        </a>
                        <a href="/giave" class="action-btn success">
                            <i class="fas fa-ticket-alt"></i>
                            Quản lý giá vé
                        </a>
                        <button class="action-btn outline" onclick="refreshData()">
                            <i class="fas fa-sync-alt"></i>
                            Làm mới
                        </button>
                        <button class="action-btn outline" onclick="exportReport()">
                            <i class="fas fa-download"></i>
                            Xuất báo cáo
                        </button>
                    </div>
                </section>
            </div>
        </main>
    </div>

    <script>
        // Enhanced functions
        function refreshData() {
            const btn = document.querySelector('[onclick="refreshData()"]');
            btn.classList.add('loading');
            btn.innerHTML = '<i class="fas fa-spinner"></i> Đang tải...';
            
            setTimeout(() => {
                location.reload();
            }, 1500);
        }

        function exportReport() {
            const btn = document.querySelector('[onclick="exportReport()"]');
            btn.classList.add('loading');
            btn.innerHTML = '<i class="fas fa-spinner"></i> Xuất...';
            
            setTimeout(() => {
                btn.classList.remove('loading');
                btn.innerHTML = '<i class="fas fa-check"></i> Hoàn thành!';
                
                setTimeout(() => {
                    btn.innerHTML = '<i class="fas fa-download"></i> Xuất báo cáo';
                }, 2000);
            }, 2000);
        }

        function animateValue(element, start, end, duration, suffix = '') {
            let startTimestamp = null;
            const step = (timestamp) => {
                if (!startTimestamp) startTimestamp = timestamp;
                const progress = Math.min((timestamp - startTimestamp) / duration, 1);
                const current = Math.floor(progress * (end - start) + start);
                element.textContent = current + suffix;
                if (progress < 1) {
                    window.requestAnimationFrame(step);
                }
            };
            window.requestAnimationFrame(step);
        }

        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -30px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('revealed');
                    
                    if (entry.target.classList.contains('stat-card')) {
                        const numberElement = entry.target.querySelector('.stat-number');
                        const target = parseInt(numberElement.getAttribute('data-target'));
                        const suffix = numberElement.textContent.includes('%') ? '%' : '';
                        
                        setTimeout(() => {
                            animateValue(numberElement, 0, target, 1500, suffix);
                        }, 200);
                    }
                }
            });
        }, observerOptions);

        document.addEventListener('DOMContentLoaded', function() {
            document.querySelectorAll('.scroll-reveal').forEach(el => {
                observer.observe(el);
            });

            document.querySelectorAll('.action-btn').forEach(btn => {
                btn.addEventListener('click', function(e) {
                    const ripple = document.createElement('span');
                    const rect = this.getBoundingClientRect();
                    const size = Math.max(rect.width, rect.height);
                    const x = e.clientX - rect.left - size / 2;
                    const y = e.clientY - rect.top - size / 2;
                    
                    ripple.style.cssText = `
                        position: absolute;
                        border-radius: 50%;
                        background: rgba(255, 255, 255, 0.6);
                        transform: scale(0);
                        animation: ripple-animation 0.4s linear;
                        left: ${x}px;
                        top: ${y}px;
                        width: ${size}px;
                        height: ${size}px;
                        pointer-events: none;
                    `;
                    
                    this.appendChild(ripple);
                    setTimeout(() => ripple.remove(), 400);
                });
            });
        });

        function updateClock() {
            const now = new Date();
            const timeString = now.toLocaleString('vi-VN', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit'
            });
            
            const timeDisplay = document.querySelector('.time-display span');
            if (timeDisplay && !timeDisplay.hasAttribute('th:text')) {
                timeDisplay.textContent = timeString;
            }
        }

        setInterval(() => {
            const timeDisplay = document.querySelector('.time-display');
            if (timeDisplay) {
                timeDisplay.style.opacity = '0.8';
                updateClock();
                setTimeout(() => {
                    timeDisplay.style.opacity = '1';
                }, 100);
            }
        }, 1000);

        const style = document.createElement('style');
        style.textContent = `
            @keyframes ripple-animation {
                to {
                    transform: scale(3);
                    opacity: 0;
                }
            }
        `;
        document.head.appendChild(style);
    </script>
</body>
</html>ass="badge bg-warning text-dark mb-1">
                        <i class="fas fa-user"></i>
                        <span th:text="${name}">Tên nhân viên</span>
                    </div>
                    <div class="badge bg-white text-dark">
                        <i class="fas fa-clock me-1"></i>
                        <span id="liveTime"></span>
                    </div>
                </div>
            </div>
            <div class="top-section">
                <div class="card gradient-card p-4">
                    <div class="row g-3 row-cols-2 row-cols-md-auto">
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-primary mb-2 mx-auto">
                                    <i class="fas fa-paw"></i>
                                </div>
                                <h4 th:text="${dongVatCount}">0</h4>
                                <small class="text-muted">Động vật</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
                            <div class="card gradient-card p-3 text-center h-100">
                                <div class="card-header-icon bg-success mb-2 mx-auto">
                                    <i class="fas fa-ticket-alt"></i>
                                </div>
                                <h4 th:text="${loaiVeCount}">0</h4>
                                <small class="text-muted">Loại vé</small>
                            </div>
                        </div>
                        <div class="col flex-fill">
             