package src;

import src.Chuong;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChuongManager {
    public List<Chuong> danhSachChuong = new ArrayList<>();
    public Scanner scanner = new Scanner(System.in);

    public void themChuong() {
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
        System.out.println("Thêm chuồng thành công");
    }

    public void hienThiTatCa() {
        if (danhSachChuong.isEmpty()) {
            System.out.println("Danh sách trống");
            return;
        }

        for (Chuong c : danhSachChuong) {
            c.hienThiThongTin();
            System.out.println("----------");
        }
    }

    public void suaChuong() {
        System.out.print("Nhập mã chuồng cần sửa: ");
        String ma = scanner.nextLine();

        for (Chuong c : danhSachChuong) {
            if (c.getMaChuong().equals(ma)) {
                System.out.println("Đã tìm thấy chuồng: " + ma);
                System.out.println("Thông tin hiện tại:");
                System.out.println("Tên khu vực: " + c.getTenKhuVuc());
                System.out.println("Sức chứa tối đa: " + c.getSucChuaToiDa());
                System.out.println("Số lượng hiện tại: " + c.getSoLuongHienTai());

                System.out.print("→ Bạn có muốn sửa thông tin chuồng này không? (y/n): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("n") || confirm.isEmpty()) {
                    System.out.println("Đã hủy thao tác sửa");
                    return;
                }

                System.out.println("→ Để trống hoặc nhập 'n' nếu không muốn thay đổi một trường nào đó");

                System.out.print("Tên khu vực mới: ");
                String khu = scanner.nextLine();
                if (!khu.isEmpty() && !khu.equalsIgnoreCase("n")) {
                    c.setTenKhuVuc(khu);
                }

                System.out.print("Sức chứa tối đa mới: ");
                String sucChuaStr = scanner.nextLine();
                if (!sucChuaStr.isEmpty() && !sucChuaStr.equalsIgnoreCase("n")) {
                    c.setSucChuaToiDa(Integer.parseInt(sucChuaStr));
                }

                System.out.print("Số lượng hiện tại mới: ");
                String soLuongStr = scanner.nextLine();
                if (!soLuongStr.isEmpty() && !soLuongStr.equalsIgnoreCase("n")) {
                    c.setSoLuongHienTai(Integer.parseInt(soLuongStr));
                }

                System.out.println("Đã cập nhật chuồng");
                return;
            }
        }

        System.out.println("❌ Không tìm thấy chuồng có mã '" + ma + "'");
    }





    public void xoaChuong() {
        System.out.print("Nhập mã chuồng cần xóa (hoặc nhập 'n' để hủy): ");
        String ma = scanner.nextLine();

        if (ma.equalsIgnoreCase("n") || ma.isEmpty()) {
            System.out.println("Hủy thao tác xóa");
            return;
        }

        for (Chuong c : danhSachChuong) {
            if (c.getMaChuong().equals(ma)) {
                danhSachChuong.remove(c);
                System.out.println("Xóa thành công");
                return;
            }
        }

        System.out.println("Không tìm thấy chuồng");
    }

}
