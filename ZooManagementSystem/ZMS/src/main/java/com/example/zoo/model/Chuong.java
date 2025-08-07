package com.example.zoo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chuong")
public class Chuong {

    @Id
    private String maChuong;
    private String tenKhuVuc;
    private int sucChuaToiDa;
    private int soLuongHienTai;

    // Constructor mặc định (bắt buộc cho JPA)
    public Chuong() {
    }

    // Constructor đầy đủ tham số (tùy ý dùng)
    public Chuong(String maChuong, String tenKhuVuc, int sucChuaToiDa, int soLuongHienTai) {
        this.maChuong = maChuong;
        this.tenKhuVuc = tenKhuVuc;
        this.sucChuaToiDa = sucChuaToiDa;
        this.soLuongHienTai = soLuongHienTai;
    }

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
}
