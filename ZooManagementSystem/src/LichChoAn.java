public class LichChoAn {
    String maLich;
    String dongVat;
    String thucAn;
    String nhanVienChoAn;
    String thoiGian;

   
    public LichChoAn() { }

    
    public LichChoAn(String ma, String dv, String ta, String nv, String tg) {
        maLich = ma;
        dongVat = dv;
        thucAn = ta;
        nhanVienChoAn = nv;
        thoiGian = tg;
    }
    

    public static void main(String[] args) {
        
        LichChoAn lich = new LichChoAn("001", "Ho", "Thit", "Dang Duc Tai", "16:30 1/7/2025");
        System.out.println("Ma lich: " + lich.maLich);
        System.out.println("Donng vat: " + lich.dongVat);
        System.out.println("Thuc an: " + lich.thucAn);
        System.out.println("Nhan vien cho an: " + lich.nhanVienChoAn);
        System.out.println("Thoi gian: " + lich.thoiGian);
    }
}
