package com.example.zoo.service;

import com.example.zoo.model.DongVat;
import com.example.zoo.repository.DongVatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DongVatService {

    @Autowired
    public DongVatRepository dongVatRepository;

    public DongVat timTheoId(Long id) {
        if (id == null || id <= 0) {
            System.err.println("Invalid DongVat ID provided: " + id);
            return null;
        }
        
        try {
            Optional<DongVat> optional = dongVatRepository.findById(id);
            DongVat result = optional.orElse(null);
            if (result == null) {
                System.err.println("Không tìm thấy động vật với ID: " + id);
            }
            return result;
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm động vật với ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<DongVat> layTatCa() {
        try {
            List<DongVat> result = dongVatRepository.findAll();
            System.out.println("DEBUG Service: Số động vật tìm thấy: " + result.size());
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy động vật: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void luuHoacCapNhat(DongVat dv) {
        if (dv == null) {
            System.err.println("Không thể lưu động vật null");
            throw new IllegalArgumentException("Động vật không được null");
        }
        
        try {
            dongVatRepository.save(dv);
            System.out.println("DEBUG: Đã lưu động vật: " + dv.getTen() + " vào chuồng: " + dv.getMaChuong());
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu động vật: " + e.getMessage());
            throw new RuntimeException("Không thể lưu động vật.", e);
        }
    }

    public void xoaTheoId(Long id) {
        if (id == null || id <= 0) {
            System.err.println("Invalid ID for delete: " + id);
            return;
        }
        
        try {
            if (dongVatRepository.existsById(id)) {
                dongVatRepository.deleteById(id);
            } else {
                System.err.println("Không tìm thấy động vật để xóa với ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa động vật: " + e.getMessage());
            throw new RuntimeException("Xóa động vật thất bại.", e);
        }
    }

    // ===== CẬP NHẬT: Lấy động vật khỏe mạnh với 3 mức sức khỏe =====
    public List<DongVat> layDongVatKhoeManhTheoThuTuChuong() {
        try {
            // Chỉ lấy động vật có sức khỏe "Tốt" và "Trung bình"
            List<DongVat> allAnimals = dongVatRepository.findAllOrderByCageAndName();
            return allAnimals.stream()
                .filter(dv -> dv.getSucKhoe() != null && 
                             (dv.getSucKhoe().equals("Tốt") || dv.getSucKhoe().equals("Trung bình")))
                .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy động vật khỏe mạnh: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<String> layTatCaLoai() {
        try {
            List<String> result = dongVatRepository.findDistinctLoai();
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách loài: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<String> layTatCaChuong() {
        try {
            List<String> result = dongVatRepository.findDistinctCages();
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách chuồng: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // ===== ALIAS METHODS CHO CONTROLLER =====
    public List<DongVat> getHealthyAnimalsOrderByCage() {
        return layDongVatKhoeManhTheoThuTuChuong();
    }

    public DongVat findById(Long id) {
        return timTheoId(id);
    }

    public List<DongVat> findByCage(String maChuong) {
        return layDongVatTheoMaChuong(maChuong);
    }

    public List<String> getAllSpecies() {
        return layTatCaLoai();
    }

    public List<String> getAllCages() {
        return layTatCaChuong();
    }

    public List<DongVat> getAll() {
        return layTatCa();
    }

    public List<DongVat> layDongVatTheoMaChuong(String maChuong) {
        if (maChuong == null || maChuong.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        try {
            List<DongVat> result = dongVatRepository.findByMaChuong(maChuong);
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy động vật theo chuồng: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<DongVat> timTheoLoai(String loai) {
        try {
            return dongVatRepository.findByLoai(loai);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm theo loài: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public long demTongSoDongVat() {
        try {
            return dongVatRepository.count();
        } catch (Exception e) {
            System.err.println("Lỗi khi đếm tổng động vật: " + e.getMessage());
            return 0;
        }
    }
}
