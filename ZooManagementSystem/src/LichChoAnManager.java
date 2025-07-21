package src;

import src.Chuong;

import java.util.List;
import java.util.Scanner;

public class LichChoAnManager {

    public List<LichChoAn> danhSach;

    public LichChoAnManager(List<LichChoAn> ds) {
        this.danhSach = ds;
    }

    public List<LichChoAn> getList() {
        return danhSach;
    }

    public List<LichChoAn> Create(LichChoAn lca) {
        try {
            danhSach.add(lca);
            System.out.println("Lich cho an da duoc them.");
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm Lịch Cho Ăn: " + e.getMessage());
        }
        return danhSach;
    }

    public List<LichChoAn> Delete(String ma) {
        try {
            boolean found = false;
            for (int i = 0; i < danhSach.size(); i++) {
                if (danhSach.get(i).maLich.equals(ma)) {
                    danhSach.remove(i);
                    found = true;
                    System.out.println("Lịch cho ăn có mã " + ma + " đã bị xóa.");
                    break;
                }
            }
            if (!found) {
                System.out.println("Không tìm thấy Lịch Cho Ăn với mã: " + ma);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa Lịch Cho Ăn: " + e.getMessage());
        }
        return danhSach;
    }

    public List<LichChoAn> Edit(String ma) {
        try {
            boolean found = false;
            for (int i = 0; i < danhSach.size(); i++) {
                if (danhSach.get(i).maLich.equals(ma)) {
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Nhập thời gian mới: ");
                    String tgMoi = scan.nextLine();
                    danhSach.get(i).thoiGian = tgMoi;
                    System.out.println("Thời gian của Lịch Cho Ăn có mã " + ma + " đã được sửa.");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Không tìm thấy Lịch Cho Ăn với mã: " + ma);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi chỉnh sửa Lịch Cho Ăn: " + e.getMessage());
        }
        return danhSach;
    }

    public void printLichChoAn() {
        try {
            if (danhSach.isEmpty()) {
                System.out.println("Danh sách Lịch Cho Ăn hiện tại rỗng.");
            } else {
                for (int i = 0; i < danhSach.size(); i++) {
                    LichChoAn l = danhSach.get(i);
                    System.out.println("Lịch: " + l.maLich + " | " + l.dongVat + " | " + l.thucAn + " | " + l.nhanVien + " | " + l.thoiGian);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi in danh sách Lịch Cho Ăn: " + e.getMessage());
        }
    }
}
