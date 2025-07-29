package test;

import manager.ChuongManager;

import java.util.Scanner;

public class TestChuongManager {
    public static void runTest() {
        ChuongManager cm = new ChuongManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENU QUẢN LÝ CHUỒNG ---");
            System.out.println("1. Thêm chuồng");
            System.out.println("2. Hiển thị tất cả");
            System.out.println("3. Sửa chuồng");
            System.out.println("4. Xóa chuồng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String chon = scanner.nextLine();

            switch (chon) {
                case "1": cm.them(); break;
                case "2": cm.hienThi(); break;
                case "3": cm.sua(); break;
                case "4": cm.xoa(); break;
                case "0": return;
                default: System.out.println("❌ Lựa chọn không hợp lệ");
            }
        }
    }
}
