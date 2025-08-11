package com.example.zoo.repository;

import com.example.zoo.model.LichChoAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LichChoAnRepository extends JpaRepository<LichChoAn, String> {
    
    List<LichChoAn> findAllByOrderByThoiGianDesc();
    
    @Query("SELECT l FROM LichChoAn l ORDER BY l.thoiGian DESC LIMIT 5")
    List<LichChoAn> findTop5ByOrderByThoiGianDesc();
    
    // Safe queries với JOIN để tránh lỗi entity không tồn tại
    @Query(value = """
        SELECT l.* FROM lich_cho_an l 
        INNER JOIN dongvat d ON l.dong_vat_id = d.id 
        INNER JOIN users u ON l.nhan_vien_id = u.id 
        WHERE l.dong_vat_id > 0 AND l.nhan_vien_id > 0
        ORDER BY l.thoi_gian DESC 
        LIMIT 5
        """, nativeQuery = true)
    List<LichChoAn> findTop5SafeByOrderByThoiGianDesc();

    @Query(value = """
        SELECT l.* FROM lich_cho_an l 
        INNER JOIN dongvat d ON l.dong_vat_id = d.id 
        INNER JOIN users u ON l.nhan_vien_id = u.id 
        WHERE l.dong_vat_id > 0 AND l.nhan_vien_id > 0
        ORDER BY l.thoi_gian DESC
        """, nativeQuery = true)
    List<LichChoAn> findAllSafeByOrderByThoiGianDesc();
    
    // Các method khác
    List<LichChoAn> findByThoiGianBetween(LocalDateTime start, LocalDateTime end);
    List<LichChoAn> findByNhanVienIdOrderByThoiGianDesc(Long nhanVienId);
    List<LichChoAn> findByDongVatIdOrderByThoiGianDesc(Long dongVatId);
    List<LichChoAn> findByTrangThaiOrderByThoiGianDesc(String trangThai);
    List<LichChoAn> findByThoiGianBetweenAndTrangThaiOrderByThoiGian(
        LocalDateTime start, LocalDateTime end, String trangThai);
    long countByTrangThai(String trangThai);
}
