package com.example.zoo.model;

public class LichChoAn {
    private String maLich;
    private String dongVat;
    private String thucAn;
    private String nhanVien;
    private String thoiGian;

    public LichChoAn() {
    }

    public LichChoAn(String maLich, String dongVat, String thucAn, String nhanVien, String thoiGian) {
        this.maLich = maLich;
        this.dongVat = dongVat;
        this.thucAn = thucAn;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
    }

    public String getMaLich() {
        return maLich;
    }

    public void setMaLich(String maLich) {
        this.maLich = maLich;
    }

    public String getDongVat() {
        return dongVat;
    }

    public void setDongVat(String dongVat) {
        this.dongVat = dongVat;
    }

    public String getThucAn() {
        return thucAn;
    }

    public void setThucAn(String thucAn) {
        this.thucAn = thucAn;
    }

    public String getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(String nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public void hienThiThongTin() {
        System.out.println("Mã lịch: " + maLich);
        System.out.println("Động vật: " + dongVat);
        System.out.println("Thức ăn: " + thucAn);
        System.out.println("Nhân viên: " + nhanVien);
        System.out.println("Thời gian: " + thoiGian);
    }
}
