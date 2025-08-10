package com.example.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "lich_cho_an")
public class LichChoAn {

    @Id
    @Column(name = "ma_lich", length = 20, nullable = false)
    @NotBlank
    @Size(max = 20)
    private String maLich;

    @Column(name = "dong_vat", length = 100, nullable = false)
    @NotBlank
    @Size(max = 100)
    private String dongVat;

    @Column(name = "thuc_an", length = 100, nullable = false)
    @NotBlank
    @Size(max = 100)
    private String thucAn;

    @Column(name = "nhan_vien", length = 120, nullable = false)
    @NotBlank
    @Size(max = 120)
    private String nhanVien;

    @Column(name = "thoi_gian", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") 
    @FutureOrPresent(message = "Thời gian phải ở hiện tại hoặc tương lai")
    private LocalDateTime thoiGian;

    public LichChoAn() {}

    public LichChoAn(String maLich, String dongVat, String thucAn, String nhanVien, LocalDateTime thoiGian) {
        this.maLich   = maLich;
        this.dongVat  = dongVat;
        this.thucAn   = thucAn;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
    }

    public String getId() { return maLich; }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (maLich != null)   maLich   = maLich.trim();
        if (dongVat != null)  dongVat  = dongVat.trim();
        if (thucAn != null)   thucAn   = thucAn.trim();
        if (nhanVien != null) nhanVien = nhanVien.trim();
        if (thoiGian == null) thoiGian = LocalDateTime.now();
    }

    // Getters/Setters
    public String getMaLich() { return maLich; }
    public void setMaLich(String maLich) { this.maLich = maLich; }

    public String getDongVat() { return dongVat; }
    public void setDongVat(String dongVat) { this.dongVat = dongVat; }

    public String getThucAn() { return thucAn; }
    public void setThucAn(String thucAn) { this.thucAn = thucAn; }

    public String getNhanVien() { return nhanVien; }
    public void setNhanVien(String nhanVien) { this.nhanVien = nhanVien; }

    public LocalDateTime getThoiGian() { return thoiGian; }
    public void setThoiGian(LocalDateTime thoiGian) { this.thoiGian = thoiGian; }
}
