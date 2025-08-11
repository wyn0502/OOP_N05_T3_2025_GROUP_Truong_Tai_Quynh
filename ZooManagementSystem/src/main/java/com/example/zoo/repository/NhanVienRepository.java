package com.example.zoo.repository;

import com.example.zoo.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {
    boolean existsByUsername(String username);
    
    List<NhanVien> findByRole(String role);
    List<NhanVien> findByChuong(String chuong);
    
    @Query("SELECT nv FROM NhanVien nv WHERE nv.role = 'staff' ORDER BY nv.fullname")
    List<NhanVien> findAllStaffOrderByName();
    
    @Query("SELECT nv FROM NhanVien nv ORDER BY nv.fullname")
    List<NhanVien> findAllOrderByName();
    
    NhanVien findByUsername(String username);
    
    @Query("SELECT nv FROM NhanVien nv WHERE nv.chuong = ?1 AND nv.role = 'staff'")
    List<NhanVien> findStaffByCage(String chuong);
}
