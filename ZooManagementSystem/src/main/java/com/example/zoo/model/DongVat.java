package com.example.zoo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dongvat")
public class DongVat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ten;
    private int tuoi;
    private String loai;

    @Column(name = "suc_khoe")
    private String sucKhoe;

    @Column(name = "ma_chuong")  // Cập nhật tên cột trong database
    private String maChuong;     // Thay đổi từ khuVuc thành maChuong

    public DongVat() {}

    public DongVat(String ten, int tuoi, String loai, String sucKhoe, String maChuong) {
        this.ten = ten;
        this.tuoi = tuoi;
        this.loai = loai;
        this.sucKhoe = sucKhoe;
        this.maChuong = maChuong;  // Thay đổi từ khuVuc thành maChuong
    }

    // GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getSucKhoe() {
        return sucKhoe;
    }

    public void setSucKhoe(String sucKhoe) {
        this.sucKhoe = sucKhoe;
    }

    public String getMaChuong() {  // Thay đổi getter
        return maChuong;
    }

    public void setMaChuong(String maChuong) {  // Thay đổi setter
        this.maChuong = maChuong;
    }

    @Override
    public String toString() {
        return "DongVat{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", tuoi=" + tuoi +
                ", loai='" + loai + '\'' +
                ", sucKhoe='" + sucKhoe + '\'' +
                ", maChuong='" + maChuong + '\'' +  // Thay đổi trong toString
                '}';
    }
}
