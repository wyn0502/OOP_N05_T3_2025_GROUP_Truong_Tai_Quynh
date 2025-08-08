package com.example.zoo.service;

import com.example.zoo.model.NhanVien;
import com.example.zoo.repository.NhanVienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NhanVienService {

    private final NhanVienRepository repo;

    public NhanVienService(NhanVienRepository repo) {
        this.repo = repo;
    }

    // ======== READ ========
    @Transactional(readOnly = true)
    public List<NhanVien> hienThiTatCa() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public NhanVien timTheoId(Long id) {
        if (id == null)
            return null;
        return repo.findById(id).orElse(null);
    }

    // ======== CREATE ========
    public NhanVien them(NhanVien nv) {
        if (nv == null) return null;

        // Không động vào id (DB tự sinh)
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

    // ======== UPDATE (partial) ========
    public NhanVien capNhat(Long id, NhanVien patch) {
        if (id == null || patch == null)
            return null;

        return repo.findById(id).map(nv -> {
            String fullname = safe(patch.getFullname());
            String phone = safe(patch.getPhone());
            String chuong = safe(patch.getChuong());
            String role = safe(patch.getRole());
            String password = safe(patch.getPassword());

            if (!isBlank(fullname))
                nv.setFullname(fullname);
            if (!isBlank(phone))
                nv.setPhone(phone);
            if (!isBlank(chuong))
                nv.setChuong(chuong);
            if (patch.getDatework() != null)
                nv.setDatework(patch.getDatework());
            if (!isBlank(role))
                nv.setRole(normalizeRole(role));
            if (!isBlank(password))
                nv.setPassword(password);

            return repo.save(nv);
        }).orElse(null);
    }

    // ======== DELETE ========
    public void xoa(Long id) {
        if (id == null)
            return;
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    // ======== Helpers ========
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
