package com.example.zoo.repository;

import com.example.zoo.model.DongVat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongVatRepository extends JpaRepository<DongVat, Long> {
    long countByMaChuong(String maChuong);
    List<DongVat> findByMaChuong(String maChuong);
    boolean existsByMaChuong(String maChuong);
    List<DongVat> findByLoai(String loai);
    List<DongVat> findBySucKhoe(String sucKhoe);
    
    List<DongVat> findByTuoiBetween(double minTuoi, double maxTuoi);
    
    DongVat findByTen(String ten);
    List<DongVat> findByTenContainingIgnoreCase(String ten);
}
