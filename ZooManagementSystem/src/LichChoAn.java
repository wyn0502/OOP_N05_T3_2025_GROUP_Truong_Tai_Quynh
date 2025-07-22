package src;

public class LichChoAn {
    private String maLich;
    private String dongVat;
    private String thucAn;
    private String nhanVien;
    private String thoiGian;

    // Constructor đầy đủ tham số
    public LichChoAn(String maLich, String dongVat, String thucAn, String nhanVien, String thoiGian) {
        this.maLich = maLich;
        this.dongVat = dongVat;
        this.thucAn = thucAn;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
    }

    // Getter & Setter như đã thêm trước đó
    public String getMaLich() {
        return maLich;
    }

    public String getDongVat() {
        return dongVat;
    }

    public String getThucAn() {
        return thucAn;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
