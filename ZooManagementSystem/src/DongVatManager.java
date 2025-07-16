package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DongVatManager {
    private List<DongVat> danhSachDongVat = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void themDongVat() {
        System.out.print("Nhập tên động vật: ");
        String ten = scanner.nextLine();
        System.out.print("Nhập tuổi động vật: ");
        int tuoi = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập loại động vật: ");
        String loai = scanner.nextLine();

        DongVat dongVat = new DongVat(ten, tuoi, loai);
        danhSachDongVat.add(dongVat);
        System.out.println("Đã thêm động vật thành công");
    }

    public void hienThiTatCaDongVat() {
        if (danhSachDongVat.isEmpty()) {
            System.out.println("Danh sách động vật trống");
        }

        for (DongVat dv : danhSachDongVat) {
            dv.hienThiThongTin();
            System.out.println("----------");
        }
    }

    public void suaDongVat() {
        System.out.print("Nhập tên động vật cần sửa: ");
        String ten = scanner.nextLine();

        for (DongVat dv : danhSachDongVat) {
            if (dv.getTen().equalsIgnoreCase(ten)) {
                System.out.println("Đã tìm thấy động vật: " + ten);
                System.out.println("Thông tin hiện tại:");
                dv.hienThiThongTin();

                System.out.print("→ Bạn có muốn sửa thông tin động vật này không? (y/n): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("n") || confirm.isEmpty()) {
                    System.out.println("Đã hủy thao tác sửa");
                }

                System.out.println("→ Để trống hoặc nhập 'n' nếu không muốn thay đổi một trường nào đó");
                System.out.print("Tên mới: ");
                String tenMoi = scanner.nextLine();
                if (!tenMoi.isEmpty() && !tenMoi.equalsIgnoreCase("n")) {
                    dv.setTen(tenMoi);
                }

                System.out.print("Tuổi mới: ");
                String tuoiMoi = scanner.nextLine();
                if (!tuoiMoi.isEmpty() && !tuoiMoi.equalsIgnoreCase("n")) {
                    dv.setTuoi(Integer.parseInt(tuoiMoi));
                }

                System.out.print("Loại mới: ");
                String loaiMoi = scanner.nextLine();
                if (!loaiMoi.isEmpty() && !loaiMoi.equalsIgnoreCase("n")) {
                    dv.setLoai(loaiMoi);
                }

                System.out.println("Đã cập nhật thông tin động vật");
            }
        }

        System.out.println("❌ Không tìm thấy động vật có tên '" + ten + "'");
    }

    public void xoaDongVat() {
        System.out.print("Nhập tên động vật cần xóa (hoặc nhập 'n' để hủy): ");
        String ten = scanner.nextLine();

        if (ten.equalsIgnoreCase("n") || ten.isEmpty()) {
            System.out.println("Đã hủy thao tác xóa");
        }

        for (DongVat dv : danhSachDongVat) {
            if (dv.getTen().equalsIgnoreCase(ten)) {
                danhSachDongVat.remove(dv);
                System.out.println("Đã xóa động vật thành công");
            }
        }

        System.out.println("Không tìm thấy động vật có tên '" + ten + "'");
    }
}
