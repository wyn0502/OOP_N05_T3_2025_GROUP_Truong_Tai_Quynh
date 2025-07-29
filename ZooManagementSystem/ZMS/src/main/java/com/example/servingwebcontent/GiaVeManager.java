package com.example.servingwebcontent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GiaVeManager {
    private List<GiaVe> danhSachVe = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void themVe() {
        try {
            System.out.print("Nhập loại vé: ");
            String loaiVe = scanner.nextLine();

            System.out.print("Nhập giá cơ bản: ");
            double giaCoBan = Double.parseDouble(scanner.nextLine());

            GiaVe ve = new GiaVe(loaiVe, giaCoBan);
            danhSachVe.add(ve);
            System.out.println("✔️ Đã thêm vé thành công.");
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: Giá phải là một số hợp lệ.");
        }
    }

    public void hienThiTatCaVe() {
        if (danhSachVe.isEmpty()) {
            System.out.println("Danh sách vé trống.");
            return;
        }

        for (GiaVe ve : danhSachVe) {
            System.out.println(ve.toString());
            System.out.println("----------");
        }
    }

    public void suaVe() {
        System.out.print("Nhập loại vé cần sửa: ");
        String loai = scanner.nextLine();

        boolean found = false;

        for (GiaVe ve : danhSachVe) {
            if (ve.getLoaiVe().equalsIgnoreCase(loai)) {
                found = true;
                System.out.println("Đã tìm thấy vé: " + loai);
                System.out.println("Thông tin hiện tại: " + ve);

                System.out.print("→ Bạn có muốn sửa thông tin vé này không? (y/n): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("n") || confirm.isEmpty()) {
                    System.out.println("Đã hủy thao tác sửa");
                    return;
                }

                System.out.print("Loại vé mới (bỏ qua nếu không muốn đổi): ");
                String loaiMoi = scanner.nextLine();
                if (!loaiMoi.isEmpty() && !loaiMoi.equalsIgnoreCase("n")) {
                    ve.setLoaiVe(loaiMoi);
                }

                System.out.print("Giá cơ bản mới: ");
                String giaMoiStr = scanner.nextLine();
                if (!giaMoiStr.isEmpty() && !giaMoiStr.equalsIgnoreCase("n")) {
                    try {
                        ve.setGiaCoBan(Double.parseDouble(giaMoiStr));
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Giá không hợp lệ, giữ nguyên giá trị cũ.");
                    }
                }

                System.out.println("✔️ Đã cập nhật thông tin vé.");
                break;
            }
        }

        if (!found) {
            System.out.println("❌ Không tìm thấy vé có loại '" + loai + "'");
        }
    }

    public void xoaVe() {
        System.out.print("Nhập loại vé cần xóa (hoặc nhập 'n' để hủy): ");
        String loai = scanner.nextLine();

        if (loai.equalsIgnoreCase("n") || loai.isEmpty()) {
            System.out.println("Đã hủy thao tác xóa");
            return;
        }

        boolean found = false;
        for (int i = danhSachVe.size() - 1; i >= 0; i--) {
            if (danhSachVe.get(i).getLoaiVe().equalsIgnoreCase(loai)) {
                danhSachVe.remove(i);
                System.out.println("✔️ Đã xóa vé thành công.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("❌ Không tìm thấy vé có loại '" + loai + "'");
        }
    }
}
