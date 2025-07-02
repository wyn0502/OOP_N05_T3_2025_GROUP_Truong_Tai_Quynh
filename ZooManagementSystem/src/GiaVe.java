package ZooManagementSystem.src;

public class GiaVe {
    private String loaiVe;
    private double giaCoBan;

    /**
     * @author TRUONG
     */

    // Constructor default
    public GiaVe() {
        this.loaiVe = "Thông thường";
        this.giaCoBan = 100_000;
    }

    // Constructor full
    public GiaVe(String loaiVe, double giaCoBan) {
        this.loaiVe = loaiVe;
        this.giaCoBan = giaCoBan;
    }

    public String getLoaiVe() { return loaiVe; }

    public void setLoaiVe(String loaiVe) { this.loaiVe = loaiVe; }

    public double getGiaCoBan() { return giaCoBan; }

    public void setGiaCoBan(double giaCoBan) { this.giaCoBan = giaCoBan; }

    // Tính giá vé theo loại khách hàng
    public double tinhGiaTheoDoiTuong(String doiTuong) {
        switch (doiTuong.toLowerCase()) {
        case "tre em":
            return giaCoBan * 0.5; // -50%
        case "sinh vien":
            return giaCoBan * 0.7; // -30%
        case "nguoi gia":
            return giaCoBan * 0.6; // -40%
        case "nguoi lon":
        default:
            return giaCoBan;
        }
    }

    public double tinhTongTien(int soLuong, String doiTuong) {
        return tinhGiaTheoDoiTuong(doiTuong) * soLuong;
    }

    public double apDungKhuyenMai(double phanTramGiamGia) {
        if (phanTramGiamGia < 0 || phanTramGiamGia > 100)
            return giaCoBan;
        return giaCoBan * (1 - phanTramGiamGia / 100.0);
    }

    @Override
    public String toString() {
        return "Loại vé: " + loaiVe + ", Giá cơ bản: " + giaCoBan + " VND";
    }
}
