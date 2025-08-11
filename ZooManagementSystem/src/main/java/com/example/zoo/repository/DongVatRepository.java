package com.example.zoo.repository;

import com.example.zoo.model.DongVat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongVatRepository extends JpaRepository<DongVat, Long> {
    long countByMaChuong(String maChuong);
    List<DongVat> findByMaChuong(String maChuong);
    boolean existsByMaChuong(String maChuong);
    List<DongVat> findByLoai(String loai);
    List<DongVat> findBySucKhoe(String sucKhoe);
    List<DongVat> findByTuoiBetween(int minTuoi, int maxTuoi);
    
    @Query("SELECT dv FROM DongVat dv ORDER BY dv.maChuong, dv.ten")
    List<DongVat> findAllOrderByCageAndName();
    
    @Query("SELECT DISTINCT dv.loai FROM DongVat dv ORDER BY dv.loai")
    List<String> findDistinctLoai();
    
    @Query("SELECT DISTINCT dv.maChuong FROM DongVat dv ORDER BY dv.maChuong")
    List<String> findDistinctCages();
    
    // Cập nhật: chỉ lấy động vật có sức khỏe Tốt và Trung bình
    @Query("SELECT dv FROM DongVat dv WHERE dv.sucKhoe IN ('Tốt', 'Trung bình') ORDER BY dv.maChuong, dv.ten")
    List<DongVat> findHealthyAnimalsOrderByCage();
}
