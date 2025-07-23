package test;

import manager.LichChoAnManager;

import java.util.Scanner;

public class TestLichChoAnManager {
    public static void runTest() {
        LichChoAnManager lm = new LichChoAnManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENU QUẢN LÝ LỊCH CHO ĂN ---");
            System.out.println("1. Thêm lịch cho ăn");
            System.out.println("2. Hiển thị tất cả lịch");
            System.out.println("3. Sửa lịch cho ăn");
            System.out.println("4. Xóa lịch cho ăn");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String chon = scanner.nextLine();

            switch (chon) {
                case "1": lm.them(); break;
                case "2": lm.hienThi(); break;
                case "3": lm.sua(); break;
                case "4": lm.xoa(); break;
                case "0": return;
                default: System.out.println("❌ Lựa chọn không hợp lệ");
            }
        }
    }
}
