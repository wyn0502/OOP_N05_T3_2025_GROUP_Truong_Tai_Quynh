package src;

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
        danhSach.add(lca);
        return danhSach;
    }

    public List<LichChoAn> Delete(String ma) {
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaLich().equals(ma)) {
                danhSach.remove(i);
            }
        }
        return danhSach;
    }

    public List<LichChoAn> Edit(String ma) {
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaLich().equals(ma)) {
                System.out.println("Nhập : ");
                Scanner scan = new Scanner(System.in);
                String newTg = scan.nextLine();
                danhSach.get(i).setThoiGian(newTg);
            }
        }
        return danhSach;
    }

    public void printLichChoAn() {
        for (int i = 0; i < danhSach.size(); i++) {
            LichChoAn l = danhSach.get(i);
            System.out.println("Lịch: " + l.getMaLich() + " | " + l.getDongVat() + " | " + l.getThucAn() + " | " + l.getNhanVien() + " | " + l.getThoiGian());
        }
    }
}
