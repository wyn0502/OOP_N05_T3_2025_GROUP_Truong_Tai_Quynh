

package ZooManagementSystem.src;

public class LichChoAn {
    
    private String maLich;
    private String dongVat;
    private String thucAn;
    private String nhanVienChoAn;
    private String thoiGian;

    
    public LichChoAn() {
    }

    
    public void vietMaLich(String maLich) {
        this.maLich = maLich;
    }

    public String hienThiMaLich() {
        return maLich;
    }

    public void chonDongVat(String dongVat) {
        this.dongVat = dongVat;
    }

    public String hienThiDongVat() {
        return dongVat;
    }

    public void chonThucAn(String thucAn) {
        this.thucAn = thucAn;
    }

    public String hienThiThucAn() {
        return thucAn;
    }

    public void chonNhanVienChoAn(String nhanVienChoAn) {
        this.nhanVienChoAn = nhanVienChoAn;
    }

    public String hienThiNhanVienChoAn() {
        return nhanVienChoAn;
    }

    public void chonThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String hienThiThoiGian() {
        return thoiGian;
    }

    
    
}
