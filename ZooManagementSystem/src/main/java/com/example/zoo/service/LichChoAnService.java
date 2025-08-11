package com.example.zoo.service;

import com.example.zoo.interfaces.IManager;
import com.example.zoo.model.LichChoAn;
import com.example.zoo.repository.LichChoAnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LichChoAnService implements IManager<LichChoAn> {

    private final LichChoAnRepository repo;

    public LichChoAnService(LichChoAnRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void them(LichChoAn lich) {
        try {
            if (lich == null) {
                throw new IllegalArgumentException("Lịch cho ăn không được null");
            }
            
            // Kiểm tra trùng mã
            if (repo.existsById(lich.getMaLich())) {
                throw new IllegalArgumentException("Mã lịch " + lich.getMaLich() + " đã tồn tại");
            }
            
            // Normalize data
            if (lich.getTrangThai() == null) {
                lich.setTrangThai("CHUA_THUC_HIEN");
            }
            if (lich.getDonVi() == null) {
                lich.setDonVi("kg");
            }
            if (lich.getNgayTao() == null) {
                lich.setNgayTao(LocalDateTime.now());
            }
            
            // Lưu với retry logic
            LichChoAn saved = repo.save(lich);
            System.out.println("✅ Successfully saved schedule: " + saved.getMaLich());
            
        } catch (Exception e) {
            System.err.println("❌ Error saving schedule: " + e.getMessage());
            throw new RuntimeException("Không thể lưu lịch cho ăn: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LichChoAn> hienThi() {
        try {
            List<LichChoAn> result = repo.findAllSafeByOrderByThoiGianDesc();
            if (result == null || result.isEmpty()) {
                // Fallback to regular query with filtering
                result = repo.findAll().stream()
                    .filter(this::isValidSchedule)
                    .sorted((a, b) -> b.getThoiGian().compareTo(a.getThoiGian()))
                    .collect(Collectors.toList());
            }
            return filterValidSchedules(result);
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách lịch: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sua(String id, LichChoAn lichMoi) {
        try {
            if (!repo.existsById(id)) {
                throw new IllegalArgumentException("Không tìm thấy lịch với mã: " + id);
            }
            
            lichMoi.setMaLich(id);
            repo.save(lichMoi);
            System.out.println("✅ Successfully updated schedule: " + id);
            
        } catch (Exception e) {
            System.err.println("❌ Error updating schedule: " + e.getMessage());
            throw new RuntimeException("Không thể cập nhật lịch cho ăn: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void xoa(String id) {
        try {
            if (!repo.existsById(id)) {
                throw new IllegalArgumentException("Không tìm thấy lịch với mã: " + id);
            }
            
            repo.deleteById(id);
            System.out.println("✅ Successfully deleted schedule: " + id);
            
        } catch (Exception e) {
            System.err.println("❌ Error deleting schedule: " + e.getMessage());
            throw new RuntimeException("Không thể xóa lịch cho ăn: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public List<LichChoAn> getAll() {
        return hienThi();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void themLich(LichChoAn lich) {
        them(lich);
    }

    @Transactional(readOnly = true)
    public LichChoAn timTheoId(String id) {
        try {
            return repo.findById(id).orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm lịch theo ID: " + e.getMessage());
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void capNhatLich(LichChoAn lichMoi) {
        sua(lichMoi.getMaLich(), lichMoi);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void xoaLich(String id) {
        xoa(id);
    }

    @Transactional(readOnly = true)
    public List<LichChoAn> getLatest(int limit) {
        try {
            List<LichChoAn> result = repo.findTop5SafeByOrderByThoiGianDesc();
            return filterValidSchedules(result);
        } catch (Exception e) {
            System.err.println("🚨 Lỗi trong getLatest: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean capNhatTrangThai(String maLich, String trangThai) {
        try {
            LichChoAn lich = timTheoId(maLich);
            if (lich != null) {
                lich.setTrangThai(trangThai);
                repo.save(lich);
                System.out.println("✅ Updated status for " + maLich + " to " + trangThai);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật trạng thái: " + e.getMessage());
            return false;
        }
    }

    // ===== HELPER METHODS =====
    
    private List<LichChoAn> filterValidSchedules(List<LichChoAn> schedules) {
        if (schedules == null) return Collections.emptyList();
        
        return schedules.stream()
            .filter(this::isValidSchedule)
            .collect(Collectors.toList());
    }

    private boolean isValidSchedule(LichChoAn lich) {
        try {
            if (lich == null) return false;
            
            if (lich.getDongVat() == null || lich.getDongVat().getId() == null || lich.getDongVat().getId() <= 0) {
                return false;
            }
            
            if (lich.getNhanVien() == null || lich.getNhanVien().getId() == null || lich.getNhanVien().getId() <= 0) {
                return false;
            }
            
            return true;
            
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi khi validate lịch: " + e.getMessage());
            return false;
        }
    }
}
