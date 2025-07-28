package test;

import java.util.ArrayList;
import java.util.Scanner;

public class TestMoiTruong {
    public static void main(String[] args) {
        ArrayList<MoiTruongSong> danhSach = new ArrayList<>();
        ZooSystemManagement zsm = new ZooSystemManagement(danhSach);
        Scanner sc = new Scanner(System.in);
        int chon;

        do {
            System.out.println("\n===== MENU MÔI TRƯỜNG SỐNG =====");
            System.out.println("1. Thêm môi trường sống");
            System.out.println("2. Xoá môi trường sống");
            System.out.println("3. Sửa môi trường sống");
            System.out.println("4. Xem danh sách môi trường sống");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            chon = sc.nextInt();
            sc.nextLine();

            switch (chon) {
                case 1:
                    System.out.print("Nhập ID: ");
                    String id = sc.nextLine();
                    System.out.print("Tên môi trường: ");
                    String ten = sc.nextLine();
                    System.out.print("Loại môi trường: ");
                    String loai = sc.nextLine();
                    zsm.create(new MoiTruongSong(id, ten, loai));
                    System.out.println("✔ Đã thêm.");
                    break;

                case 2:
                    System.out.print("Nhập ID muốn xoá: ");
                    String idXoa = sc.nextLine();
                    zsm.delete(idXoa);
                    break;

                case 3:
                    System.out.print("Nhập ID muốn sửa: ");
                    String idSua = sc.nextLine();
                    zsm.edit(idSua);
                    break;

                case 4:
                    zsm.printDanhSach();
                    break;

                case 0:
                    System.out.println("👋 Thoát chương trình.");
                    break;

                default:
                    System.out.println("⚠ Lựa chọn không hợp lệ.");
            }
        } while (chon != 0);
    }
}
