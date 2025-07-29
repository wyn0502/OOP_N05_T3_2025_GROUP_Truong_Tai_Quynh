package com.example.zoo.model;

public class Chuong {
    private String maChuong;
    private String tenKhuVuc;
    private int sucChuaToiDa;
    private int soLuongHienTai;

    public Chuong(String maChuong, String tenKhuVuc, int sucChuaToiDa, int soLuongHienTai) {
        this.maChuong = maChuong;
        this.tenKhuVuc = tenKhuVuc;
        this.sucChuaToiDa = sucChuaToiDa;
        this.soLuongHienTai = soLuongHienTai;
    }

    public String getMaChuong() {
        return maChuong;
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

    public void hienThiThongTin() {
        System.out.println("Mã chuồng: " + maChuong);
        System.out.println("Tên khu vực: " + tenKhuVuc);
        System.out.println("Sức chứa tối đa: " + sucChuaToiDa);
        System.out.println("Số lượng hiện tại: " + soLuongHienTai);
    }
}
