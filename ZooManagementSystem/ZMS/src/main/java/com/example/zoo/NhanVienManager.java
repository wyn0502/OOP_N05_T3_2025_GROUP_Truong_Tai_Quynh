package com.example.zoo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NhanVienManager {
    private List<NhanVien> danhSachNhanVien = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void themNhanVien() {
        System.out.print("Nhập mã nhân viên: ");
        String ma = scanner.nextLine();

        System.out.print("Nhập tên nhân viên: ");
        String ten = scanner.nextLine();

        System.out.print("Nhập cấp bậc: ");
        String capBac = scanner.nextLine();

        System.out.print("Nhập thông tin cá nhân: ");
        String thongTin = scanner.nextLine();

        System.out.print("Nhập số điện thoại: ");
        String sdt = scanner.nextLine();

        System.out.print("Nhập chuồng phụ trách: ");
        String chuong = scanner.nextLine();

        NhanVien nv = new NhanVien(ma, ten, capBac, thongTin, sdt, chuong);
        danhSachNhanVien.add(nv);
        System.out.println("✔️ Đã thêm nhân viên thành công.");
    }

    public void hienThiTatCaNhanVien() {
        if (danhSachNhanVien.isEmpty()) {
            System.out.println("⚠️ Danh sách nhân viên trống.");
            return;
        }

        for (NhanVien nv : danhSachNhanVien) {
            nv.hienThiThongTin();
            System.out.println("----------");
        }
    }

    public void suaNhanVien() {
        System.out.print("Nhập mã nhân viên cần sửa: ");
        String ma = scanner.nextLine();
        boolean found = false;

        for (NhanVien nv : danhSachNhanVien) {
            if (nv.getMaNhanVien().equalsIgnoreCase(ma)) {
                found = true;
                System.out.println("🔍 Thông tin hiện tại:");
                nv.hienThiThongTin();

                System.out.println("→ Để trống hoặc nhập 'n' nếu không muốn thay đổi một trường.");

                System.out.print("Tên mới: ");
                String tenMoi = scanner.nextLine();
                if (!tenMoi.isEmpty() && !tenMoi.equalsIgnoreCase("n")) {
                    nv.setTenNhanVien(tenMoi);
                }

                System.out.print("Cấp bậc mới: ");
                String capBacMoi = scanner.nextLine();
                if (!capBacMoi.isEmpty() && !capBacMoi.equalsIgnoreCase("n")) {
                    nv.setCapBac(capBacMoi);
                }

                System.out.print("Thông tin cá nhân mới: ");
                String ttcnMoi = scanner.nextLine();
                if (!ttcnMoi.isEmpty() && !ttcnMoi.equalsIgnoreCase("n")) {
                    nv.setThongTinCaNhan(ttcnMoi);
                }

                System.out.print("SĐT mới: ");
                String sdtMoi = scanner.nextLine();
                if (!sdtMoi.isEmpty() && !sdtMoi.equalsIgnoreCase("n")) {
                    nv.setSoDienThoai(sdtMoi);
                }

                System.out.print("Chuồng phụ trách mới: ");
                String chuongMoi = scanner.nextLine();
                if (!chuongMoi.isEmpty() && !chuongMoi.equalsIgnoreCase("n")) {
                    nv.setChuongPhuTrach(chuongMoi);
                }

                System.out.println("✔️ Đã cập nhật thông tin nhân viên.");
                break;
            }
        }

        if (!found) {
            System.out.println("❌ Không tìm thấy nhân viên có mã '" + ma + "'");
        }
    }

    public void xoaNhanVien() {
        System.out.print("Nhập mã nhân viên cần xóa (hoặc 'n' để hủy): ");
        String ma = scanner.nextLine();

        if (ma.equalsIgnoreCase("n") || ma.isEmpty()) {
            System.out.println("❎ Đã hủy thao tác xóa.");
            return;
        }

        boolean found = false;
        for (int i = danhSachNhanVien.size() - 1; i >= 0; i--) {
            if (danhSachNhanVien.get(i).getMaNhanVien().equalsIgnoreCase(ma)) {
                danhSachNhanVien.remove(i);
                System.out.println("✔️ Đã xóa nhân viên thành công.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("❌ Không tìm thấy nhân viên có mã '" + ma + "'");
        }
    }
}
