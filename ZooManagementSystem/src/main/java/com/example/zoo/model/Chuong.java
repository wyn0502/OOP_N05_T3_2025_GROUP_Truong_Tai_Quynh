package com.example.zoo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "chuong")
public class Chuong {

    @Id
    @Column(name = "ma_chuong")
    @NotBlank(message = "Mã chuồng không được để trống")
    private String maChuong;

    @Column(name = "ten_khu_vuc")
    @NotBlank(message = "Tên khu vực không được để trống")
    private String tenKhuVuc;

    @Column(name = "suc_chua_toi_da")
    @Min(value = 1, message = "Sức chứa tối đa phải lớn hơn 0")
    private int sucChuaToiDa;

    @Column(name = "so_luong_hien_tai")
    @Min(value = 0, message = "Số lượng hiện tại không được âm")
    private int soLuongHienTai;

    // Mối quan hệ One-to-Many với DongVat
    @OneToMany(mappedBy = "chuong", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DongVat> danhSachDongVat;

    // Constructors
    public Chuong() {
    }

    public Chuong(String maChuong, String tenKhuVuc, int sucChuaToiDa, int soLuongHienTai) {
        this.maChuong = maChuong;
        this.tenKhuVuc = tenKhuVuc;
        this.sucChuaToiDa = sucChuaToiDa;
        this.soLuongHienTai = soLuongHienTai;
    }

    @PrePersist
    @PreUpdate
    private void validateChuong() {
        if (soLuongHienTai < 0) {
            soLuongHienTai = 0;
        }
        
        if (soLuongHienTai > sucChuaToiDa) {
            throw new IllegalStateException(
                String.format("Số lượng hiện tại (%d) không thể lớn hơn sức chứa tối đa (%d)", 
                             soLuongHienTai, sucChuaToiDa));
        }
        
        if (sucChuaToiDa <= 0) {
            throw new IllegalStateException("Sức chứa tối đa phải lớn hơn 0");
        }
    }

    // Getters và Setters
    public String getMaChuong() {
        return maChuong;
    }

    public void setMaChuong(String maChuong) {
        this.maChuong = maChuong;
    }

    public String getTenKhuVuc() {
        return tenKhuVuc;
    }

    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }

    public int getSucChuaToiDa() {
        return sucChuaToiDa;
    }

    public void setSucChuaToiDa(int sucChuaToiDa) {
        this.sucChuaToiDa = sucChuaToiDa;
    }

    public int getSoLuongHienTai() {
        return soLuongHienTai;
    }

    public void setSoLuongHienTai(int soLuongHienTai) {
        this.soLuongHienTai = soLuongHienTai;
    }

    public List<DongVat> getDanhSachDongVat() {
        return danhSachDongVat;
    }

    public void setDanhSachDongVat(List<DongVat> danhSachDongVat) {
        this.danhSachDongVat = danhSachDongVat;
    }

    // Utility methods
    public boolean coChoTrong() {
        return soLuongHienTai < sucChuaToiDa;
    }

    public int soChoTrong() {
        return sucChuaToiDa - soLuongHienTai;
    }

    @Override
    public String toString() {
        return "Chuong{" +
                "maChuong='" + maChuong + '\'' +
                ", tenKhuVuc='" + tenKhuVuc + '\'' +
                ", sucChuaToiDa=" + sucChuaToiDa +
                ", soLuongHienTai=" + soLuongHienTai +
                '}';
    }
}
