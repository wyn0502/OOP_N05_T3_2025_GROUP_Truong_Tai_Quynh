package com.example.zoo.service;

import com.example.zoo.model.DongVat;
import com.example.zoo.repository.DongVatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DongVatService {

    @Autowired
    public DongVatRepository dongVatRepository;

    public List<DongVat> layTatCa() {
        try {
            List<DongVat> result = dongVatRepository.findAll();
            System.out.println("DEBUG Service: Số động vật tìm thấy: " + result.size());
            return result;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy động vật: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void luuHoacCapNhat(DongVat dv) {
        try {
            dongVatRepository.save(dv);
            System.out.println("DEBUG: Đã lưu động vật: " + dv.getTen() + " vào chuồng: " + dv.getMaChuong());
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu động vật: " + e.getMessage());
            throw new RuntimeException("Không thể lưu động vật.", e);
        }
    }

    public DongVat timTheoId(Long id) {
        try {
            Optional<DongVat> optional = dongVatRepository.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm động vật: " + e.getMessage());
            return null;
        }
    }

    public void xoaTheoId(Long id) {
        try {
            dongVatRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa động vật: " + e.getMessage());
            throw new RuntimeException("Xóa động vật thất bại.", e);
        }
    }

    // ===== THÊM CÁC METHOD THIẾU =====

    // THÊM METHOD ĐẾM THEO MÃ CHUỒNG
    public long demDongVatTheoMaChuong(String maChuong) {
        try {
            long count = dongVatRepository.countByMaChuong(maChuong);
            System.out.println("DEBUG: Số động vật trong chuồng " + maChuong + ": " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Lỗi khi đếm động vật: " + e.getMessage());
            return 0;
        }
    }

    // LẤY DANH SÁCH ĐỘNG VẬT THEO CHUỒNG
    public List<DongVat> layDongVatTheoMaChuong(String maChuong) {
        try {
            List<DongVat> result = dongVatRepository.findByMaChuong(maChuong);
            System.out.println("DEBUG: Tìm thấy " + result.size() + " động vật trong chuồng " + maChuong);
            for (DongVat dv : result) {
                System.out.println("  - " + dv.getTen() + " (ID: " + dv.getId() + ")");
            }
            return result;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy động vật theo chuồng: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // THÊM CÁC METHOD HỖ TRỢ KHÁC
    public List<DongVat> timTheoLoai(String loai) {
        try {
            return dongVatRepository.findByLoai(loai);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm theo loài: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<DongVat> timTheoSucKhoe(String sucKhoe) {
        try {
            return dongVatRepository.findBySucKhoe(sucKhoe);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm theo sức khỏe: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<DongVat> timTheoKhoangTuoi(int minTuoi, int maxTuoi) {
        try {
            return dongVatRepository.findByTuoiBetween(minTuoi, maxTuoi);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm theo khoảng tuổi: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    // THỐNG KÊ
    public long demTongSoDongVat() {
        try {
            return dongVatRepository.count();
        } catch (Exception e) {
            System.err.println("Lỗi khi đếm tổng động vật: " + e.getMessage());
            return 0;
        }
    }
}
