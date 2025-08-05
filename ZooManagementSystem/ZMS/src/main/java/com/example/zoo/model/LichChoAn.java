package com.example.zoo.model;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LichChoAn {

    @NotBlank(message = "Vui lòng nhập mã lịch")
    private String maLich;

    @NotBlank(message = "Vui lòng nhập động vật cho ăn")
    private String dongVat;

    @NotBlank(message = "Vui lòng nhập tên thức ăn")
    private String thucAn;

    @NotBlank(message = "Vui lòng nhập tên nhân viên")
    private String nhanVien;

    @NotBlank(message = "Vui lòng nhập thời gian cho ăn")
    private String thoiGian; // ISO 8601 format: "2025-08-05T10:30"

    public LichChoAn() {
        this.maLich = java.util.UUID.randomUUID().toString();
        this.dongVat = "";
        this.thucAn = "";
        this.nhanVien = "";
        this.thoiGian = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public LichChoAn(String maLich, String dongVat, String thucAn, String nhanVien, String thoiGian) {
        this.maLich = maLich;
        this.dongVat = dongVat;
        this.thucAn = thucAn;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
    }

    // Getter & Setter
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

    // Logic tính thời gian tiếp theo
    public String tinhThoiGianKeTiep(int soGio) {
        try {
            LocalDateTime time = LocalDateTime.parse(thoiGian);
            return time.plusHours(soGio).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            return "Thời gian không hợp lệ";
        }
    }

    public boolean laLichTrongNgay() {
        try {
            LocalDateTime tg = LocalDateTime.parse(thoiGian);
            return tg.toLocalDate().equals(LocalDateTime.now().toLocalDate());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Mã lịch: " + maLich
                + ", Động vật: " + dongVat
                + ", Thức ăn: " + thucAn
                + ", Nhân viên: " + nhanVien
                + ", Thời gian: " + thoiGian;
    }
}
