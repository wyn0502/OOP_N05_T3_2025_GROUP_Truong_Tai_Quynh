// package src;

// import ZooManagementSystem.src.Chuong;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;

/**
 * @author TRUONG
 */

// public class Chuong {
//     private List<Chuong> danhSachChuong = new ArrayList<>();
//     private Scanner scanner = new Scanner(System.in);

//     // CREATE
//     public void themChuong() {
//         System.out.print("Nhập mã chuồng: ");
//         String ma = scanner.nextLine();
//         System.out.print("Nhập tên khu vực: ");
//         String khu = scanner.nextLine();
//         System.out.print("Nhập sức chứa tối đa: ");
//         int sucChua = Integer.parseInt(scanner.nextLine());
//         System.out.print("Nhập số lượng hiện tại: ");
//         int soLuong = Integer.parseInt(scanner.nextLine());

//         Chuong c = new Chuong(ma, khu, sucChua, soLuong);
//         danhSachChuong.add(c);
//         System.out.println("Thêm chuồng thành công!");
//     }

//     // READ
//     public void hienThiTatCa() {
//         for (Chuong c : danhSachChuong) {
//             c.hienThiThongTin();
//             System.out.println("----------");
//         }
//     }

//     // UPDATE
//     public void suaChuong() {
//         System.out.print("Nhập mã chuồng cần sửa: ");
//         String ma = scanner.nextLine();
//         for (Chuong c : danhSachChuong) {
//             if (c.MaChuong.equals(ma)) {
//                 System.out.print("Nhập tên khu vực mới: ");
//                 c.Tenkhuvuc = scanner.nextLine();
//                 System.out.print("Nhập sức chứa tối đa mới: ");
//                 c.SucChuaToiDa = Integer.parseInt(scanner.nextLine());
//                 System.out.print("Nhập số lượng hiện tại mới: ");
//                 c.SoLuongHienTai = Integer.parseInt(scanner.nextLine());
//                 System.out.println("Sửa thành công!");
//                 return;
//             }
//         }
//         System.out.println("Không tìm thấy chuồng!");
//     }

//     // DELETE
//     public void xoaChuong() {
//         System.out.print("Nhập mã chuồng cần xóa: ");
//         String ma = scanner.nextLine();
//         for (Chuong c : danhSachChuong) {
//             if (c.MaChuong.equals(ma)) {
//                 danhSachChuong.remove(c);
//                 System.out.println("Xóa thành công!");
//                 return;
//             }
//         }
//         System.out.println("Không tìm thấy chuồng!");
//     }
// }
