package com.example.zoo.model;

/**
 * @author TRUONG
 */

public class DongVat {
    private String ten;
    private int tuoi;
    private String loai;

    public DongVat() {
    }

    public DongVat(String ten, int tuoi, String loai) {
        this.ten = ten;
        this.tuoi = tuoi;
        this.loai = loai;
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

    public void hienThiThongTin() {
        System.out.println("Ten: " + ten);
        System.out.println("Tuoi: " + tuoi);
        System.out.println("Loai: " + loai);
    }

    public void keu() {
        switch (loai.toLowerCase()) {
            case "khi":
                System.out.println("Khi: ec ec!");
                break;
            case "su tu":
                System.out.println("Su tu: Gam gu!");
                break;
            default:
                System.out.println("Dong vat: ...");
        }
    }
}
