package com.example.zoo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "chuong")
public class Chuong {

    @Id
    @Column(name = "ma_chuong")
    private String maChuong;

    @Column(name = "ten_khu_vuc")
    private String tenKhuVuc;

    @Column(name = "suc_chua_toi_da")
    private int sucChuaToiDa;

    @Column(name = "so_luong_hien_tai")
    private int soLuongHienTai;

    // Mối quan hệ One-to-Many với DongVat
    @OneToMany(mappedBy = "chuong", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DongVat> danhSachDongVat;

    // Constructors, getters, setters...
    public Chuong() {
    }

    public Chuong(String maChuong, String tenKhuVuc, int sucChuaToiDa, int soLuongHienTai) {
        this.maChuong = maChuong;
        this.tenKhuVuc = tenKhuVuc;
        this.sucChuaToiDa = sucChuaToiDa;
        this.soLuongHienTai = soLuongHienTai;
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
}
