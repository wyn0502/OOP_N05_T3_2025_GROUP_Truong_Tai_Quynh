package com.example.zoo.repository;

import com.example.zoo.model.DongVat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongVatRepository extends JpaRepository<DongVat, Long> {
    
    // Các method hiện có
    DongVat findByTen(String ten); 
    void deleteByTen(String ten);
    
    // THÊM CÁC METHOD CẦN THIẾT CHO MÃ CHUỒNG
    
    // Đếm số động vật theo mã chuồng
    long countByMaChuong(String maChuong);
    
    // Lấy danh sách động vật theo mã chuồng
    List<DongVat> findByMaChuong(String maChuong);
    
    // Kiểm tra có động vật nào trong chuồng không
    boolean existsByMaChuong(String maChuong);
    
    // Tìm động vật theo loài
    List<DongVat> findByLoai(String loai);
    
    // Tìm động vật theo sức khỏe
    List<DongVat> findBySucKhoe(String sucKhoe);
    
    // Tìm động vật theo khoảng tuổi
    List<DongVat> findByTuoiBetween(int minTuoi, int maxTuoi);
}
