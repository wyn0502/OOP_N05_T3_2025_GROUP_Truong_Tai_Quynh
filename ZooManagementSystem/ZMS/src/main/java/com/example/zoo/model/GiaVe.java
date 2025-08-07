package com.example.zoo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.example.zoo.interfaces.IHasId;

@Entity
@Table(name = "giave")
public class GiaVe implements IHasId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vui lòng chọn loại vé")
    @Column(name = "loai_ve", nullable = false)
    private String loaiVe;

    @NotNull(message = "Vui lòng nhập giá vé")
    @DecimalMin(value = "1000", message = "Giá vé phải ít nhất 1,000 VNĐ")
    @Column(name = "gia_co_ban", nullable = false)
    private Double giaCoBan;

    @Column(name = "ly_do_giam_gia")
    private String lyDoGiamGia;

    @Min(value = 0, message = "Phần trăm giảm giá phải từ 0 đến 100")
    @Max(value = 100, message = "Phần trăm giảm giá phải từ 0 đến 100")
    @Column(name = "phan_tram_giam_gia")
    private Double phanTramGiamGia;

    public GiaVe() {
        this.loaiVe = "";
        this.giaCoBan = 100000.0;
        this.lyDoGiamGia = "";
        this.phanTramGiamGia = 0.0;
    }

    public GiaVe(String loaiVe, Double giaCoBan, String lyDoGiamGia, Double phanTramGiamGia) {
        this.loaiVe = loaiVe;
        this.giaCoBan = giaCoBan;
        this.lyDoGiamGia = lyDoGiamGia;
        this.phanTramGiamGia = phanTramGiamGia;
    }

    @Override
    public String getId() {
        return id == null ? null : id.toString();
    }

    public Long getIdRaw() {  // Nếu cần lấy giá trị Long cho repo
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getLoaiVe() { return loaiVe; }
    public void setLoaiVe(String loaiVe) { this.loaiVe = loaiVe; }

    public Double getGiaCoBan() { return giaCoBan; }
    public void setGiaCoBan(Double giaCoBan) { this.giaCoBan = giaCoBan; }

    public String getLyDoGiamGia() { return lyDoGiamGia; }
    public void setLyDoGiamGia(String lyDoGiamGia) { this.lyDoGiamGia = lyDoGiamGia; }

    public Double getPhanTramGiamGia() { return phanTramGiamGia; }
    public void setPhanTramGiamGia(Double phanTramGiamGia) { this.phanTramGiamGia = phanTramGiamGia; }

    public double tinhGiaTheoDoiTuong(String doiTuong) {
        double gia = giaCoBan != null ? giaCoBan : 0;
        return switch (doiTuong.toLowerCase()) {
            case "tre em" -> gia * 0.5;
            case "sinh vien" -> gia * 0.7;
            case "nguoi gia" -> gia * 0.6;
            default -> gia;
        };
    }

    public double tinhTongTien(int soLuong, String doiTuong) {
        return tinhGiaTheoDoiTuong(doiTuong) * soLuong;
    }

    public double apDungKhuyenMai() {
        double gia = giaCoBan != null ? giaCoBan : 0;
        double giam = phanTramGiamGia != null ? phanTramGiamGia : 0;
        return (giam >= 0 && giam <= 100) ? gia * (1 - giam / 100.0) : gia;
    }

    @Override
    public String toString() {
        return "GiaVe{" +
                "id=" + id +
                ", loaiVe='" + loaiVe + '\'' +
                ", giaCoBan=" + giaCoBan +
                ", lyDoGiamGia='" + lyDoGiamGia + '\'' +
                ", phanTramGiamGia=" + phanTramGiamGia +
                '}';
    }
}
