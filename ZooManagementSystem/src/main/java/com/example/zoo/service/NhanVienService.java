package com.example.zoo.service;

import com.example.zoo.model.NhanVien;
import com.example.zoo.repository.NhanVienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NhanVienService {

    private final NhanVienRepository repo;

    public NhanVienService(NhanVienRepository repo) {
        this.repo = repo;
    }

    // Lấy tất cả
    @Transactional(readOnly = true)
    public List<NhanVien> hienThiTatCa() {
        return repo.findAll();
    }

    // Thêm mới (ép role = staff)
    @Transactional
    public NhanVien them(NhanVien nv) {
        if (nv == null) return null;

        nv.setId(safe(nv.getId()));
        nv.setFullname(safe(nv.getFullname()));
        nv.setUsername(safe(nv.getUsername()));
        nv.setPhone(safe(nv.getPhone()));
        nv.setChuong(safe(nv.getChuong()));
       
        nv.setRole("staff");
        

        return repo.save(nv);
    }

    // Tìm theo id
    @Transactional(readOnly = true)
    public NhanVien timTheoId(String id) {
        if (id == null) return null;
        return repo.findById(id).orElse(null);
    }

    
    @Transactional
    public NhanVien capNhat(String id, NhanVien patch) {
        if (id == null || patch == null) return null;

        return repo.findById(id).map(nv -> {
            String fullname = safe(patch.getFullname());
            String phone    = safe(patch.getPhone());
            String chuong   = safe(patch.getChuong());

            if (!isBlank(fullname)) nv.setFullname(fullname);
            if (!isBlank(phone))    nv.setPhone(phone);
            if (!isBlank(chuong))   nv.setChuong(chuong);
            if (patch.getDatework() != null) nv.setDatework(patch.getDatework());

          
            nv.setRole("staff");

            return repo.save(nv);
        }).orElse(null);
    }

  
    @Transactional
    public void xoa(String id) {
        if (id == null) return;
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

   
    private static String safe(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
