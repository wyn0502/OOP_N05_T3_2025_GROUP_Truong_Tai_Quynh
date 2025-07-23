package manager;

import model.LichChoAn;
import interfaces.IManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LichChoAnManager implements IManager {
    private final List<LichChoAn> danhSachLich = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void them() {
        System.out.print("Nhập mã lịch: ");
        String maLich = scanner.nextLine();

        System.out.print("Nhập tên động vật: ");
        String dongVat = scanner.nextLine();

        System.out.print("Nhập tên thức ăn: ");
        String thucAn = scanner.nextLine();

        System.out.print("Nhập tên nhân viên: ");
        String nhanVien = scanner.nextLine();

        System.out.print("Nhập thời gian cho ăn: ");
        String thoiGian = scanner.nextLine();

        LichChoAn lich = new LichChoAn(maLich, dongVat, thucAn, nhanVien, thoiGian);
        danhSachLich.add(lich);
        System.out.println("✅ Thêm lịch cho ăn thành công");
    }

    @Override
    public void hienThi() {
        if (danhSachLich.isEmpty()) {
            System.out.println("Danh sách lịch cho ăn trống.");
        } else {
            for (LichChoAn lich : danhSachLich) {
                lich.hienThiThongTin();
                System.out.println("----------");
            }
        }
    }

    @Override
    public void sua() {
        System.out.print("Nhập mã lịch cần sửa: ");
        String ma = scanner.nextLine();

        for (LichChoAn lich : danhSachLich) {
            if (lich.getMaLich().equals(ma)) {
                System.out.print("Tên động vật mới (Enter để giữ nguyên): ");
                String dongVat = scanner.nextLine();
                if (!dongVat.isEmpty()) {
                    lich.setDongVat(dongVat);
                }

                System.out.print("Tên thức ăn mới (Enter để giữ nguyên): ");
                String thucAn = scanner.nextLine();
                if (!thucAn.isEmpty()) {
                    lich.setThucAn(thucAn);
                }

                System.out.print("Tên nhân viên mới (Enter để giữ nguyên): ");
                String nhanVien = scanner.nextLine();
                if (!nhanVien.isEmpty()) {
                    lich.setNhanVien(nhanVien);
                }

                System.out.print("Thời gian mới (Enter để giữ nguyên): ");
                String thoiGian = scanner.nextLine();
                if (!thoiGian.isEmpty()) {
                    lich.setThoiGian(thoiGian);
                }

                System.out.println("✅ Đã cập nhật lịch cho ăn");
                return;
            }
        }

        System.out.println("❌ Không tìm thấy lịch cho ăn");
    }

    @Override
    public void xoa() {
        System.out.print("Nhập mã lịch cần xóa: ");
        String ma = scanner.nextLine();

        for (int i = 0; i < danhSachLich.size(); i++) {
            if (danhSachLich.get(i).getMaLich().equals(ma)) {
                danhSachLich.remove(i);
                System.out.println("✅ Đã xóa lịch cho ăn");
                return;
            }
        }

        System.out.println("❌ Không tìm thấy lịch cho ăn");
    }
}
