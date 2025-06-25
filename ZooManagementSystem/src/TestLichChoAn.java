public class TestLichChoAn {
    public static void inThoiGian() {
        
        LichChoAn lich = new LichChoAn();

        
        lich.vietMaLich("001");
        lich.chonDongVat("Ho");
        lich.chonThucAn("Thit");
        lich.chonNhanVienChoAn("Nguyen Van A");
        lich.chonThoiGian("25/06/2025 15:17");

        
        
        System.out.println("Ma lich: " + lich.hienThiMaLich());
        System.out.println("Dong vat: " + lich.hienThiDongVat());
        System.out.println("Thuc an: " + lich.hienThiThucAn());
        System.out.println("Nhan vien cho an: " + lich.hienThiNhanVienChoAn());
        System.out.println("Thoi gian: " + lich.hienThiThoiGian());
    }
}
    
