package com.example.zoo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class NhanVien {

    @Id
    @Column(length = 50, nullable = false, updatable = false)
    private String id;                    

    @Column(nullable = false, length = 120)
    private String fullname;

    @Column(nullable = false, length = 60, unique = true)
    private String username;

    @Column(nullable = false, length = 20)
    private String role = "staff";         // mặc định staff

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column
    private LocalDate datework;           

    @Column(length = 20)
    private String phone;

    @Column(length = 50)
    private String chuong;

    public NhanVien() {}

    public NhanVien(String id, String fullname, String username, String role,
                    LocalDate datework, String phone, String chuong) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.role = role;
        this.datework = datework;
        this.phone = phone;
        this.chuong = chuong;
    }

    // ===== Getters / Setters =====
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getDatework() { return datework; }
    public void setDatework(LocalDate datework) { this.datework = datework; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getChuong() { return chuong; }
    public void setChuong(String chuong) { this.chuong = chuong; }

    @Override
    public String toString() {
        return "NhanVien{" +
                "id='" + id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", datework=" + datework +
                ", phone='" + phone + '\'' +
                ", chuong='" + chuong + '\'' +
                '}';
    }
}
