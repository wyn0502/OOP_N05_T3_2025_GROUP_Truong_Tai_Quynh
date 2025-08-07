package com.example.zoo.model;

import java.time.LocalDate;

public class NhanVien {
    private String id;
    private String fullname;
    private String username;
    private String role;
    private String datework;
    private String phone;
    private String chuong;

    public NhanVien() {
    }

    public NhanVien(String id, String fullname, String username, String role, LocalDate datework, String phone, String chuong) {
    this.id = id;
    this.fullname = fullname;
    this.username = username;
    this.role = role;
    this.datework = datework.toString(); 
    this.phone = phone;
    this.chuong = chuong;
}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDatework() {
        return datework;
    }

    public void setDatework(String datework) {
        this.datework = datework;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChuong() {
        return chuong;
    }

    public void setChuong(String chuong) {
        this.chuong = chuong;
    }

    @Override
    public String toString() {
        return "Mã nhân viên: " + id +
                "\nTên: " + fullname +
                "\nUsername: " + username +
                "\nCấp bậc: " + role +
                "\nNgày vào làm: " + datework +
                "\nSĐT: " + phone +
                "\nChuồng phụ trách: " + chuong;
    }
}
