package src;
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

    // Các method setter
    public void vietMaLich(String ma) {
        this.maLich = ma;
    }

    public void chonDongVat(String dv) {
        this.dongVat = dv;
    }

    public void chonThucAn(String ta) {
        this.thucAn = ta;
    }

    public void chonNhanVienChoAn(String nv) {
        this.nhanVienChoAn = nv;
    }

    public void chonThoiGian(String tg) {
        this.thoiGian = tg;
    }

    // Các method getter
    public String hienThiMaLich() {
        return maLich;
    }

    public String hienThiDongVat() {
        return dongVat;
    }

    public String hienThiThucAn() {
        return thucAn;
    }

    public String hienThiNhanVienChoAn() {
        return nhanVienChoAn;
    }

    public String hienThiThoiGian() {
        return thoiGian;
    }
}
