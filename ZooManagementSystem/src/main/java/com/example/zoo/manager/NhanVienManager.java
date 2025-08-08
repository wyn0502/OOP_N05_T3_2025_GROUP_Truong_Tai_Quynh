package com.example.zoo.manager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.zoo.model.NhanVien;

public class NhanVienManager {
    private final List<NhanVien> danhSachNhanVien = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    private Long parseLongOrNull(String s) {
        try {
            return Long.parseLong(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    public void themNhanVien() {
        System.out.print("Nhập mã nhân viên (id - số): ");
        String idStr = scanner.nextLine().trim();
        Long id = parseLongOrNull(idStr);
        if (id == null) {
            System.out.println("⛔ ID phải là số. Hủy thao tác.");
            return;
        }

        System.out.print("Nhập họ tên (fullname): ");
        String fullname = scanner.nextLine().trim();

        System.out.print("Nhập tài khoản (username): ");
        String username = scanner.nextLine().trim();

        // Chỉ cho phép staff
        String role = "staff";

        System.out.print("Nhập ngày vào làm (YYYY-MM-DD): ");
        LocalDate datework;
        try {
            datework = LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("⛔ Ngày không hợp lệ. Dùng mặc định hôm nay.");
            datework = LocalDate.now();
        }

        System.out.print("Nhập số điện thoại (phone): ");
        String phone = scanner.nextLine().trim();

        System.out.print("Nhập chuồng phụ trách (chuong): ");
        String chuong = scanner.nextLine().trim();

        // KHÔNG dùng constructor sai chữ ký; dùng setter cho khớp entity thật
        NhanVien nv = new NhanVien();
        nv.setId(id);
        nv.setFullname(fullname);
        nv.setUsername(username);
        nv.setPassword("123456"); 
        nv.setRole(role);
        nv.setDatework(datework);
        nv.setPhone(phone);
        nv.setChuong(chuong);

        danhSachNhanVien.add(nv);
        System.out.println("✔️ Đã thêm nhân viên thành công.");
    }

    public void hienThiTatCaNhanVien() {
        if (danhSachNhanVien.isEmpty()) {
            System.out.println("⚠️ Danh sách nhân viên trống.");
            return;
        }
        for (NhanVien nv : danhSachNhanVien) {
            System.out.printf(
                "NV[%s] %s | user:%s | role:%s | phone:%s | chuong:%s | datework:%s%n",
                nv.getId(), nv.getFullname(), nv.getUsername(), nv.getRole(),
                nv.getPhone(), nv.getChuong(), nv.getDatework()
            );
            System.out.println("----------");
        }
    }

    public void suaNhanVien() {
        System.out.print("Nhập mã nhân viên (id - số) cần sửa: ");
        String idStr = scanner.nextLine().trim();
        Long id = parseLongOrNull(idStr);
        if (id == null) {
            System.out.println("⛔ ID phải là số.");
            return;
        }

        NhanVien target = danhSachNhanVien.stream()
            .filter(nv -> nv.getId() != null && nv.getId().equals(id))
            .findFirst()
            .orElse(null);

        if (target == null) {
            System.out.println("❌ Không tìm thấy nhân viên có id '" + id + "'");
            return;
        }

        System.out.println("🔍 Thông tin hiện tại:");
        System.out.printf(
            "NV[%s] %s | user:%s | role:%s | phone:%s | chuong:%s | datework:%s%n",
            target.getId(), target.getFullname(), target.getUsername(), target.getRole(),
            target.getPhone(), target.getChuong(), target.getDatework()
        );
        System.out.println("→ Để trống hoặc nhập 'n' nếu không muốn thay đổi một trường.");

        System.out.print("Họ tên mới: ");
        String fullname = scanner.nextLine().trim();
        if (!fullname.isEmpty() && !"n".equalsIgnoreCase(fullname)) {
            target.setFullname(fullname);
        }

        // Role luôn là staff
        target.setRole("staff");

        System.out.print("SĐT mới: ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty() && !"n".equalsIgnoreCase(phone)) {
            target.setPhone(phone);
        }

        System.out.print("Chuồng phụ trách mới: ");
        String chuong = scanner.nextLine().trim();
        if (!chuong.isEmpty() && !"n".equalsIgnoreCase(chuong)) {
            target.setChuong(chuong);
        }

        System.out.println("✔️ Đã cập nhật thông tin nhân viên.");
    }

    public void xoaNhanVien() {
        System.out.print("Nhập mã nhân viên (id - số) cần xóa (hoặc 'n' để hủy): ");
        String idStr = scanner.nextLine().trim();
        if (idStr.equalsIgnoreCase("n") || idStr.isEmpty()) {
            System.out.println("❎ Đã hủy thao tác xóa.");
            return;
        }
        Long id = parseLongOrNull(idStr);
        if (id == null) {
            System.out.println("⛔ ID phải là số.");
            return;
        }

        boolean removed = danhSachNhanVien.removeIf(nv -> nv.getId() != null && nv.getId().equals(id));
        if (removed) {
            System.out.println("✔️ Đã xóa nhân viên thành công.");
        } else {
            System.out.println("❌ Không tìm thấy nhân viên có id '" + id + "'");
        }
    }
}
