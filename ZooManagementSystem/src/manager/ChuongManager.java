package manager;

import model.Chuong;
import interfaces.IManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChuongManager implements IManager {
    private final List<Chuong> danhSachChuong = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void them() {
        try {
            System.out.print("Nhập mã chuồng: ");
            String ma = scanner.nextLine();

            System.out.print("Nhập tên khu vực: ");
            String khu = scanner.nextLine();

            System.out.print("Nhập sức chứa tối đa: ");
            int sucChua = Integer.parseInt(scanner.nextLine());

            System.out.print("Nhập số lượng hiện tại: ");
            int soLuong = Integer.parseInt(scanner.nextLine());

            Chuong c = new Chuong(ma, khu, sucChua, soLuong);
            danhSachChuong.add(c);
            System.out.println("✅ Thêm chuồng thành công");
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Vui lòng nhập số hợp lệ cho sức chứa và số lượng!");
        }
    }

    @Override
    public void hienThi() {
        if (danhSachChuong.isEmpty()) {
            System.out.println("Danh sách chuồng trống.");
        } else {
            for (Chuong c : danhSachChuong) {
                c.hienThiThongTin();
                System.out.println("----------");
            }
        }
    }

    @Override
    public void sua() {
        System.out.print("Nhập mã chuồng cần sửa: ");
        String ma = scanner.nextLine();

        for (Chuong c : danhSachChuong) {
            if (c.getMaChuong().equals(ma)) {
                System.out.print("Tên khu vực mới (Enter để giữ nguyên): ");
                String khu = scanner.nextLine();
                if (!khu.isEmpty()) c.setTenKhuVuc(khu);

                try {
                    System.out.print("Sức chứa tối đa mới (Enter để giữ nguyên): ");
                    String s1 = scanner.nextLine();
                    if (!s1.isEmpty()) c.setSucChuaToiDa(Integer.parseInt(s1));
                } catch (NumberFormatException e) {
                    System.out.println("Giữ nguyên sức chứa vì nhập không hợp lệ.");
                }

                try {
                    System.out.print("Số lượng hiện tại mới (Enter để giữ nguyên): ");
                    String s2 = scanner.nextLine();
                    if (!s2.isEmpty()) c.setSoLuongHienTai(Integer.parseInt(s2));
                } catch (NumberFormatException e) {
                    System.out.println("Giữ nguyên số lượng vì nhập không hợp lệ.");
                }

                System.out.println("✅ Đã cập nhật chuồng");
                return;
            }
        }

        System.out.println("❌ Không tìm thấy chuồng");
    }

    @Override
    public void xoa() {
        System.out.print("Nhập mã chuồng cần xóa: ");
        String ma = scanner.nextLine();

        for (int i = 0; i < danhSachChuong.size(); i++) {
            if (danhSachChuong.get(i).getMaChuong().equals(ma)) {
                danhSachChuong.remove(i);
                System.out.println("✅ Đã xóa chuồng");
                return;
            }
        }

        System.out.println("❌ Không tìm thấy chuồng");
    }
}
