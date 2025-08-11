package com.example.zoo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dong_vat_id", nullable = false)
    @NotNull(message = "Phải chọn động vật")
    private DongVat dongVat;

    @Column(name = "thuc_an", length = 100, nullable = false)
    @NotBlank
    @Size(max = 100)
    private String thucAn;

    @Column(name = "so_luong")
    private Double soLuong;

    @Column(name = "don_vi", length = 20)
    @Size(max = 20)
    private String donVi = "kg";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nhan_vien_id", nullable = false)
    @NotNull(message = "Phải chọn nhân viên")
    private NhanVien nhanVien;

    // ===== FIX DATETIME ANNOTATION =====
    @Column(name = "thoi_gian", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime thoiGian;

    @Column(name = "trang_thai", length = 20)
    @Size(max = 20)
    private String trangThai = "CHUA_THUC_HIEN";

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    // Constructors
    public LichChoAn() {}

    public LichChoAn(String maLich, DongVat dongVat, String thucAn, NhanVien nhanVien, LocalDateTime thoiGian) {
        this.maLich = maLich;
        this.dongVat = dongVat;
        this.thucAn = thucAn;
        this.nhanVien = nhanVien;
        this.thoiGian = thoiGian;
    }

    public String getId() { return maLich; }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (maLich != null) maLich = maLich.trim();
        if (thucAn != null) thucAn = thucAn.trim();
        if (thoiGian == null) thoiGian = LocalDateTime.now();
        if (ngayTao == null) ngayTao = LocalDateTime.now();
    }

    // Getters/Setters
    public String getMaLich() { return maLich; }
    public void setMaLich(String maLich) { this.maLich = maLich; }

    public DongVat getDongVat() { return dongVat; }
    public void setDongVat(DongVat dongVat) { this.dongVat = dongVat; }

    public String getThucAn() { return thucAn; }
    public void setThucAn(String thucAn) { this.thucAn = thucAn; }

    public Double getSoLuong() { return soLuong; }
    public void setSoLuong(Double soLuong) { this.soLuong = soLuong; }

    public String getDonVi() { return donVi; }
    public void setDonVi(String donVi) { this.donVi = donVi; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public LocalDateTime getThoiGian() { return thoiGian; }
    public void setThoiGian(LocalDateTime thoiGian) { this.thoiGian = thoiGian; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public LocalDateTime getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDateTime ngayTao) { this.ngayTao = ngayTao; }
}
