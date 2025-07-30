package com.example.zoo.model;

public class GiaVe {
    private String id;
    private String loaiVe;
    private double giaCoBan;

    public GiaVe() {
        this.id = java.util.UUID.randomUUID().toString();
        this.loaiVe = "Thông thường";
        this.giaCoBan = 100_000;
    }

    public GiaVe(String id, String loaiVe, double giaCoBan) {
        this.id = id;
        this.loaiVe = loaiVe;
        this.giaCoBan = giaCoBan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoaiVe() {
        return loaiVe;
    }

    public void setLoaiVe(String loaiVe) {
        this.loaiVe = loaiVe;
    }

    public double getGiaCoBan() {
        return giaCoBan;
    }

    public void setGiaCoBan(double giaCoBan) {
        this.giaCoBan = giaCoBan;
    }

    public double tinhGiaTheoDoiTuong(String doiTuong) {
        switch (doiTuong.toLowerCase()) {
            case "tre em": return giaCoBan * 0.5;
            case "sinh vien": return giaCoBan * 0.7;
            case "nguoi gia": return giaCoBan * 0.6;
            default: return giaCoBan;
        }
    }

    public double tinhTongTien(int soLuong, String doiTuong) {
        return tinhGiaTheoDoiTuong(doiTuong) * soLuong;
    }

    public double apDungKhuyenMai(double phanTramGiamGia) {
        if (phanTramGiamGia < 0 || phanTramGiamGia > 100) return giaCoBan;
        return giaCoBan * (1 - phanTramGiamGia / 100.0);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Loại vé: " + loaiVe + ", Giá cơ bản: " + giaCoBan + " VND";
    }
}
