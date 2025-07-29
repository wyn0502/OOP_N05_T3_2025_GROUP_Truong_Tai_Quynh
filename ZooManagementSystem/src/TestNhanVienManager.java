package src;

import java.util.Scanner;

public class TestNhanVienManager {
    public static void test() {
        NhanVienManager manager = new NhanVienManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== QUẢN LÝ NHÂN VIÊN =====");
            System.out.println("1. Thêm nhân viên");
            System.out.println("2. Hiển thị danh sách nhân viên");
            System.out.println("3. Sửa thông tin nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("0. Thoát");
            System.out.print("→ Nhập lựa chọn: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Vui lòng nhập số: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Bỏ dòng

            switch (choice) {
                case 1:
                    manager.themNhanVien();
                    break;
                case 2:
                    manager.hienThiTatCaNhanVien();
                    break;
                case 3:
                    manager.suaNhanVien();
                    break;
                case 4:
                    manager.xoaNhanVien();
                    break;
                case 0:
                    System.out.println("👋 Thoát chương trình.");
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
