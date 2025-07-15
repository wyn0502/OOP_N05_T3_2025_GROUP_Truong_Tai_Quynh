package test;

import java.util.List;
import java.util.Scanner;

public class ZooSystemManagement {
    private List<MoiTruongSong> danhSachMoiTruong;

    public ZooSystemManagement(List<MoiTruongSong> ds) {
        this.danhSachMoiTruong = ds;
    }

    public List<MoiTruongSong> getList() {
        return danhSachMoiTruong;
    }

    public List<MoiTruongSong> create(MoiTruongSong m) {
        danhSachMoiTruong.add(m);
        return danhSachMoiTruong;
    }

    public List<MoiTruongSong> delete(String id) {
        for (int i = 0; i < danhSachMoiTruong.size(); i++) {
            if (danhSachMoiTruong.get(i).id.equals(id)) {
                danhSachMoiTruong.remove(i);
                break;
            }
        }
        return danhSachMoiTruong;
    }

    public List<MoiTruongSong> edit(String id) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < danhSachMoiTruong.size(); i++) {
            if (danhSachMoiTruong.get(i).id.equals(id)) {
                System.out.print("Tên môi trường mới: ");
                String ten = scanner.nextLine();
                System.out.print("Loại môi trường mới: ");
                String loai = scanner.nextLine();
                danhSachMoiTruong.get(i).tenMoiTruong = ten;
                danhSachMoiTruong.get(i).loaiMoiTruong = loai;
                break;
            }
        }
        return danhSachMoiTruong;
    }

    public void printDanhSach() {
        if (danhSachMoiTruong.isEmpty()) {
            System.out.println("📭 Danh sách môi trường trống.");
        } else {
            System.out.println("\n📋 Danh sách môi trường sống:");
            for (MoiTruongSong m : danhSachMoiTruong) {
                System.out.println("- ID: " + m.id + " | Tên: " + m.tenMoiTruong + " | Loại: " + m.loaiMoiTruong);
            }
        }
    }
}
