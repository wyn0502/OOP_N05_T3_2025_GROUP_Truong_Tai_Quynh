package com.example.zoo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class GiaVe {
    private String id;

    @NotBlank(message = "Vui lòng chọn loại vé")
    private String loaiVe;

    @NotNull(message = "Vui lòng nhập giá vé")
    @DecimalMin(value = "1000", message = "Giá vé phải ít nhất 1,000 VNĐ")
    private Double giaCoBan;

    private String lyDoGiamGia;
    
    @Min(value = 0, message = "Phần trăm giảm giá phải từ 0 đến 100")
    @Max(value = 100, message = "Phần trăm giảm giá phải từ 0 đến 100")
    private Double phanTramGiamGia;

    public GiaVe() {
        this.id = java.util.UUID.randomUUID().toString();
        this.loaiVe = "";
        this.giaCoBan = 100000.0;
        this.lyDoGiamGia = "";
        this.phanTramGiamGia = 0.0;
    }

    public GiaVe(String id, String loaiVe, Double giaCoBan, String lyDoGiamGia, Double phanTramGiamGia) {
        this.id = id;
        this.loaiVe = loaiVe;
        this.giaCoBan = giaCoBan;
        this.lyDoGiamGia = lyDoGiamGia;
        this.phanTramGiamGia = phanTramGiamGia;
    }

    // Getter và Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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
        switch (doiTuong.toLowerCase()) {
            case "tre em": return gia * 0.5;
            case "sinh vien": return gia * 0.7;
            case "nguoi gia": return gia * 0.6;
            default: return gia;
        }
    }

    public double tinhTongTien(int soLuong, String doiTuong) {
        return tinhGiaTheoDoiTuong(doiTuong) * soLuong;
    }

    public double apDungKhuyenMai() {
        double gia = giaCoBan != null ? giaCoBan : 0;
        double phanTram = phanTramGiamGia != null ? phanTramGiamGia : 0;
        if (phanTram < 0 || phanTram > 100) return gia;
        return gia * (1 - phanTram / 100.0);
    }

    @Override
    public String toString() {
        return "ID: " + id
                + ", Loại vé: " + loaiVe
                + ", Giá cơ bản: " + giaCoBan + " VND"
                + ", Lý do giảm giá: " + lyDoGiamGia
                + ", % Giảm giá: " + phanTramGiamGia + "%";
    }
}
