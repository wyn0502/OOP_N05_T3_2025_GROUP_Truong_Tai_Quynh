package com.example.zoo;

public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private String capBac;
    private String thongTinCaNhan;
    private String soDienThoai;
    private String chuongPhuTrach;

    public NhanVien(String ma, String ten, String capBac, String thongTin, String sdt, String chuong) {
        this.maNhanVien = ma;
        this.tenNhanVien = ten;
        this.capBac = capBac;
        this.thongTinCaNhan = thongTin;
        this.soDienThoai = sdt;
        this.chuongPhuTrach = chuong;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String ma) {
        this.maNhanVien = ma;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String ten) {
        this.tenNhanVien = ten;
    }

    public String getCapBac() {
        return capBac;
    }

    public void setCapBac(String capBac) {
        this.capBac = capBac;
    }

    public String getThongTinCaNhan() {
        return thongTinCaNhan;
    }

    public void setThongTinCaNhan(String thongTinCaNhan) {
        this.thongTinCaNhan = thongTinCaNhan;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getChuongPhuTrach() {
        return chuongPhuTrach;
    }

    public void setChuongPhuTrach(String chuongPhuTrach) {
        this.chuongPhuTrach = chuongPhuTrach;
    }

    @Override
    public String toString() {
        return "Mã NV: " + maNhanVien + "\nTên: " + tenNhanVien + "\nCấp bậc: " + capBac +
                "\nThông tin: " + thongTinCaNhan + "\nSĐT: " + soDienThoai + "\nChuồng phụ trách: " + chuongPhuTrach;
    }
}
