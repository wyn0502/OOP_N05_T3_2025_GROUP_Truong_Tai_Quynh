package com.example.zoo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id BIGINT AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, length = 120)
    private String fullname;

    @Column(nullable = false, length = 60, unique = true)
    private String username;

    @Column(nullable = false, length = 255) // đủ dài cho hash nếu sau này mã hoá
    private String password;                // <-- THÊM

    @Column(nullable = false, length = 20)
    private String role = "staff";          // cho phép set tuỳ ý (admin/staff)

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datework;

    @Column(length = 20)
    private String phone;

    @Column(length = 50)
    private String chuong;

    public NhanVien() {}

    public NhanVien(Long id, String fullname, String username, String password,
                    String role, LocalDate datework, String phone, String chuong) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.datework = datework;
        this.phone = phone;
        this.chuong = chuong;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }          // <-- THÊM
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getDatework() { return datework; }
    public void setDatework(LocalDate datework) { this.datework = datework; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getChuong() { return chuong; }
    public void setChuong(String chuong) { this.chuong = chuong; }
}
