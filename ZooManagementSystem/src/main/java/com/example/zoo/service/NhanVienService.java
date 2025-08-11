package com.example.zoo.service;

import com.example.zoo.model.NhanVien;
import com.example.zoo.repository.NhanVienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class NhanVienService {

    private final NhanVienRepository repo;

    public NhanVienService(NhanVienRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public NhanVien timTheoId(Long id) {
        if (id == null || id <= 0) {
            System.err.println("Invalid NhanVien ID provided: " + id);
            return null;
        }
        
        try {
            NhanVien result = repo.findById(id).orElse(null);
            if (result == null) {
                System.err.println("Không tìm thấy nhân viên với ID: " + id);
            }
            return result;
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm nhân viên với ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<NhanVien> hienThiTatCa() {
        try {
            List<NhanVien> result = repo.findAll();
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy tất cả nhân viên: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<NhanVien> layTatCaNhanVienStaffTheoTen() {
        try {
            List<NhanVien> result = repo.findAllStaffOrderByName();
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy nhân viên staff: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<NhanVien> timStaffTheoChuong(String chuong) {
        try {
            return repo.findStaffByCage(chuong);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm staff theo chuồng: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // ===== ALIAS METHODS =====
    public List<NhanVien> getAllStaffOrderByName() {
        return layTatCaNhanVienStaffTheoTen();
    }

    public NhanVien findById(Long id) {
        return timTheoId(id);
    }

    public List<NhanVien> findStaffByCage(String chuong) {
        return timStaffTheoChuong(chuong);
    }

    public List<NhanVien> getAll() {
        return hienThiTatCa();
    }

    // Các methods khác...
    public NhanVien them(NhanVien nv) {
        if (nv == null) return null;

        nv.setFullname(safe(nv.getFullname()));
        nv.setUsername(safe(nv.getUsername()));
        nv.setPhone(safe(nv.getPhone()));
        nv.setChuong(safe(nv.getChuong()));
        nv.setRole(normalizeRole(nv.getRole()));
        nv.setPassword(safe(nv.getPassword()));

        if (!isBlank(nv.getUsername()) && repo.existsByUsername(nv.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }

        if (isBlank(nv.getPassword())) {
            throw new IllegalArgumentException("Password không được để trống");
        }

        return repo.save(nv);
    }

    private static String safe(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String normalizeRole(String role) {
        if (isBlank(role))
            return "staff";
        return role.equalsIgnoreCase("admin") ? "admin" : "staff";
    }
}
