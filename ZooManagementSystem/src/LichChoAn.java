import java.util.List;
import java.util.Scanner;

public class LichChoAn {

    public List<LichChoAn> danhSach;

    public LichChoAn(List<LichChoAn> ds) {
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
            if (danhSach.get(i).maLich.equals(ma)) {
                danhSach.remove(i);
            }
        }
        return danhSach;
    }

    public List<LichChoAn> Edit(String ma) {
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).maLich.equals(ma)) {
                Scanner scan = new Scanner(System.in);
                System.out.print("Nhap thoi gian moi: ");
                String tgMoi = scan.nextLine();
                danhSach.get(i).thoiGian = tgMoi;
            }
        }
        return danhSach;
    }

    public void printLichChoAn() {
        for (int i = 0; i < danhSach.size(); i++) {
            LichChoAn l = danhSach.get(i);
            System.out.println("Lich: " + l.maLich + " | " + l.dongVat + " | " + l.thucAn + " | " + l.nhanVien + " | " + l.thoiGian);
        }
    }
}