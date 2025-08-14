package com.example.zoo.service;

import com.example.zoo.model.NhanVien;
import com.example.zoo.repository.NhanVienRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class NhanVienService {

    @Autowired
private NhanVienRepository nhanVienRepository;

    private final NhanVienRepository repo;
    private final PasswordEncoder passwordEncoder;

   public List<NhanVien> timTheoFullname(String keyword) {
        return nhanVienRepository.findByFullnameContainingIgnoreCase(keyword);
    }

    public List<NhanVien> timKiem(String type, String keyword) {
        if (type == null || keyword == null || keyword.trim().isEmpty()) {
            return nhanVienRepository.findAll();
        }
        

        switch (type) {
            case "id":
                try {
                    Long id = Long.parseLong(keyword);
                    Optional<NhanVien> optional = nhanVienRepository.findById(id);
                    return optional.map(List::of).orElse(Collections.emptyList());
                } catch (NumberFormatException e) {
                    return Collections.emptyList();
                }

            case "username":
                NhanVien nv = nhanVienRepository.findByUsername(keyword);
                return nv != null ? List.of(nv) : Collections.emptyList();

            case "fullname":
                return timTheoFullname(keyword);

            default:
                return Collections.emptyList();
        }
    }

    public List<NhanVien> timKiem(String keyword) {
    try {
        Long id = Long.parseLong(keyword);
        return nhanVienRepository.findByIdOrUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCase(
            id, keyword, keyword);
    } catch (NumberFormatException e) {
        return nhanVienRepository.findByIdOrUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCase(
            -1L, keyword, keyword);
    }
}

    public NhanVienService(NhanVienRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        
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

    @Transactional(readOnly = true)
    public NhanVien timTheoUsername(String username) {
        if (isBlank(username)) return null;
        return repo.findByUsername(username.trim());
    }

    // ======== CREATE ========
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

        // 🔐 Mã hoá mật khẩu nếu chưa được mã hoá
        if (!isEncoded(nv.getPassword())) {
            nv.setPassword(passwordEncoder.encode(nv.getPassword()));
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
            if (!isBlank(password)) {
                // 🔐 Mã hoá lại nếu chưa mã hoá
                nv.setPassword(isEncoded(password) ? password : passwordEncoder.encode(password));
            }

            return repo.save(nv);
        }).orElse(null);
    }

    // ======== DELETE ========
    public void xoa(Long id) {
        if (id == null) return;
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    // ======== BCRYPT MIGRATION ========
    public void upgradeAllNhanVienPasswordsToBCrypt() {
        List<NhanVien> list = repo.findAll();
        int count = 0;

        for (NhanVien nv : list) {
            String pw = nv.getPassword();
            if (!isBlank(pw) && !isEncoded(pw)) {
                nv.setPassword(passwordEncoder.encode(pw));
                count++;
            }
        }

        repo.saveAll(list);
        System.out.println("Đã mã hoá mật khẩu cho " + count + " nhân viên dưới dạng BCrypt.");
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

    private static boolean isEncoded(String password) {
        // BCrypt password thường bắt đầu bằng $2a$, $2b$ hoặc $2y$
        return password != null && password.startsWith("$2");
    }
}