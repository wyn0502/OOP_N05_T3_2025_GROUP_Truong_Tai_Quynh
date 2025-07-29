package src;

import java.util.Scanner;

public class TestGiaVeManager {
    public static void chayThu(String[] args) {
        GiaVeManager giaVeManager = new GiaVeManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== QUẢN LÝ GIÁ VÉ =====");
            System.out.println("1. Thêm vé");
            System.out.println("2. Hiển thị tất cả vé");
            System.out.println("3. Sửa vé");
            System.out.println("4. Xóa vé");
            System.out.println("0. Thoát");
            System.out.print("→ Chọn chức năng: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Vui lòng nhập số hợp lệ: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng xuống sau khi nhập số

            switch (choice) {
                case 1:
                    giaVeManager.themVe();
                    break;
                case 2:
                    giaVeManager.hienThiTatCaVe();
                    break;
                case 3:
                    giaVeManager.suaVe();
                    break;
                case 4:
                    giaVeManager.xoaVe();
                    break;
                case 0:
                    System.out.println("👋 Thoát chương trình quản lý giá vé.");
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
