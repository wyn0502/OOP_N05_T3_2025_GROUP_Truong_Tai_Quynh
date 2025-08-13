package com.example.zoo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dongvat")
public class DongVat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ten;
    
    private double tuoi;
    
    private String loai;

    @Column(name = "suc_khoe")
    private String sucKhoe;

    @Column(name = "ma_chuong")
    private String maChuong;

    // mối quan hệ Many-to-One với Chuong
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_chuong", referencedColumnName = "ma_chuong",
            insertable = false, updatable = false)
    private Chuong chuong;

    // Constructors
    public DongVat() {
    }

    public DongVat(String ten, double tuoi, String loai, String sucKhoe, String maChuong) {
        this.ten = ten;
        this.tuoi = tuoi;
        this.loai = loai;
        this.sucKhoe = sucKhoe;
        this.maChuong = maChuong;
    }

    // Getters and Setters
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

    public double getTuoi() {
        return tuoi;
    }

    public void setTuoi(double tuoi) {
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

    public String getMaChuong() {
        return maChuong;
    }

    public void setMaChuong(String maChuong) {
        this.maChuong = maChuong;
    }

    public Chuong getChuong() {
        return chuong;
    }

    public void setChuong(Chuong chuong) {
        this.chuong = chuong;
    }

    @Override
    public String toString() {
        return "DongVat{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", tuoi=" + tuoi +
                ", loai='" + loai + '\'' +
                ", sucKhoe='" + sucKhoe + '\'' +
                ", maChuong='" + maChuong + '\'' +
                '}';
    }
}
