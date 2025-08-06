package com.example.zoo.model;

import java.util.Random;

public class DongVat {
    private String id;
    private String ten;
    private int tuoi;
    private String loai;
    private String sucKhoe;  // Thuộc tính mới

    public DongVat() {
        this.id = generateRandomId();
    }

    public DongVat(String ten, int tuoi, String loai, String sucKhoe) {
        this.id = generateRandomId();
        this.ten = ten;
        this.tuoi = tuoi;
        this.loai = loai;
        this.sucKhoe = sucKhoe;
    }

    private String generateRandomId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }

        return result.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void hienThiThongTin() {
        System.out.println("ID: " + id);
        System.out.println("Ten: " + ten);
        System.out.println("Tuoi: " + tuoi);
        System.out.println("Loai: " + loai);
        System.out.println("Sức khỏe: " + sucKhoe);
    }

    public void keu() {
        switch (loai.toLowerCase()) {
            case "khi":
                System.out.println("Khi: ec ec!");
                break;
            case "su tu":
                System.out.println("Su tu: Gam gu!");
                break;
            case "voi":
                System.out.println("Voi: Pawoo!");
                break;
            case "cop":
                System.out.println("Cop: Roar roar!");
                break;
            case "gau":
                System.out.println("Gau: Groooowl!");
                break;
            default:
                System.out.println("Dong vat: ...");
        }
    }

    @Override
    public String toString() {
        return "DongVat{" +
                "id='" + id + '\'' +
                ", ten='" + ten + '\'' +
                ", tuoi=" + tuoi +
                ", loai='" + loai + '\'' +
                ", sucKhoe='" + sucKhoe + '\'' +
                '}';
    }
}
