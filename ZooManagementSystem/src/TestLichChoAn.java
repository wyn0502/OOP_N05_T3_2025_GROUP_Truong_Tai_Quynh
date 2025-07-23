package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestLichChoAn {
    public static void runTest() {
        Scanner scanner = new Scanner(System.in);
        List<LichChoAn> danhSach = new ArrayList<>();

        System.out.print("Nhập số lượng lịch cho ăn: ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("Nhập thông tin cho bản ghi thứ " + (i + 1));

            System.out.print("Mã lịch: ");
            String maLich = scanner.nextLine();

            System.out.print("Tên động vật: ");
            String tenDongVat = scanner.nextLine();

            System.out.print("Ngày cho ăn (yyyy-mm-dd): ");
            LocalDate ngay = LocalDate.parse(scanner.nextLine());

            System.out.print("Thời gian: ");
            String thoiGian = scanner.nextLine();

            System.out.print("Người phụ trách: ");
            String nguoiPhuTrach = scanner.nextLine();

            danhSach.add(new LichChoAn(MaLich, tenDongVat, ngay, thoiGian, nguoiPhuTrach));
        }

        LichChoAn.hienThiLichChoAnTrongNgay(danhSach);
    }
}
