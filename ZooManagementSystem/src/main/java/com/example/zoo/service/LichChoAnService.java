package com.example.zoo.service;

import com.example.zoo.interfaces.IManager;
import com.example.zoo.model.LichChoAn;
import com.example.zoo.repository.LichChoAnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

@Service
@Transactional
public class LichChoAnService implements IManager<LichChoAn> {

    private static final Logger logger = LoggerFactory.getLogger(LichChoAnService.class);
    
    private final LichChoAnRepository repo;

    public LichChoAnService(LichChoAnRepository repo) {
        this.repo = repo;
    }

    @Override
    public void them(LichChoAn lich) {
        try {
            if (lich == null) {
                logger.error("Không thể thêm lịch cho ăn: đối tượng null");
                throw new IllegalArgumentException("Lịch cho ăn không được null");
            }
            repo.save(lich);
            logger.info("Đã thêm thành công lịch cho ăn với ID: {}", lich.getMaLich());
        } catch (Exception e) {
            logger.error("Lỗi khi thêm lịch cho ăn với ID {}: ", lich != null ? lich.getMaLich() : "null", e);
            throw new RuntimeException("Không thể thêm lịch cho ăn: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<LichChoAn> hienThi() {
        try {
            List<LichChoAn> result = repo.findAllByOrderByThoiGianDesc();
            logger.info("Lấy danh sách lịch cho ăn thành công, số lượng: {}", result.size());
            return result;
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách lịch cho ăn: ", e);
            // Trả về danh sách rỗng thay vì throw exception để tránh crash UI
            return new ArrayList<>();
        }
    }

    @Override
    public void sua(String id, LichChoAn lichMoi) {
        try {
            if (id == null || id.trim().isEmpty()) {
                logger.error("Không thể sửa lịch: ID null hoặc rỗng");
                throw new IllegalArgumentException("ID lịch không được null hoặc rỗng");
            }
            
            if (lichMoi == null) {
                logger.error("Không thể sửa lịch với ID {}: đối tượng lịch mới null", id);
                throw new IllegalArgumentException("Lịch cho ăn mới không được null");
            }

            if (repo.existsById(id)) {
                lichMoi.setMaLich(id);
                repo.save(lichMoi);
                logger.info("Đã cập nhật thành công lịch cho ăn với ID: {}", id);
            } else {
                logger.warn("Không tìm thấy lịch cho ăn với ID: {}", id);
                throw new IllegalArgumentException("Không tìm thấy lịch cho ăn với ID: " + id);
            }
        } catch (IllegalArgumentException e) {
            // Re-throw validation exceptions
            throw e;
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật lịch cho ăn với ID {}: ", id, e);
            throw new RuntimeException("Không thể cập nhật lịch cho ăn: " + e.getMessage(), e);
        }
    }

    @Override
    public void xoa(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                logger.error("Không thể xóa lịch: ID null hoặc rỗng");
                throw new IllegalArgumentException("ID lịch không được null hoặc rỗng");
            }

            if (repo.existsById(id)) {
                repo.deleteById(id);
                logger.info("Đã xóa thành công lịch cho ăn với ID: {}", id);
            } else {
                logger.warn("Không tìm thấy lịch cho ăn với ID để xóa: {}", id);
                throw new IllegalArgumentException("Không tìm thấy lịch cho ăn với ID: " + id);
            }
        } catch (IllegalArgumentException e) {
            // Re-throw validation exceptions
            throw e;
        } catch (Exception e) {
            logger.error("Lỗi khi xóa lịch cho ăn với ID {}: ", id, e);
            throw new RuntimeException("Không thể xóa lịch cho ăn: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public List<LichChoAn> getAll() {
        try {
            return hienThi();
        } catch (Exception e) {
            logger.error("Lỗi trong getAll(): ", e);
            return new ArrayList<>();
        }
    }

    public void themLich(LichChoAn lich) {
        try {
            them(lich);
        } catch (Exception e) {
            logger.error("Lỗi trong themLich(): ", e);
            throw e; // Re-throw để controller có thể handle
        }
    }

    @Transactional(readOnly = true)
    public LichChoAn timTheoId(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                logger.warn("Tìm kiếm lịch với ID null hoặc rỗng");
                return null;
            }

            LichChoAn result = repo.findById(id).orElse(null);
            if (result != null) {
                logger.info("Tìm thấy lịch cho ăn với ID: {}", id);
            } else {
                logger.info("Không tìm thấy lịch cho ăn với ID: {}", id);
            }
            return result;
        } catch (Exception e) {
            logger.error("Lỗi khi tìm lịch cho ăn với ID {}: ", id, e);
            return null; // Trả về null thay vì throw exception
        }
    }

    public void capNhatLich(LichChoAn lichMoi) {
        try {
            if (lichMoi == null) {
                logger.error("Không thể cập nhật lịch: đối tượng null");
                throw new IllegalArgumentException("Lịch cho ăn không được null");
            }
            
            if (lichMoi.getMaLich() == null || lichMoi.getMaLich().trim().isEmpty()) {
                logger.error("Không thể cập nhật lịch: ID null hoặc rỗng");
                throw new IllegalArgumentException("ID lịch không được null hoặc rỗng");
            }

            repo.save(lichMoi);
            logger.info("Đã cập nhật thành công lịch cho ăn với ID: {}", lichMoi.getMaLich());
        } catch (IllegalArgumentException e) {
            // Re-throw validation exceptions
            throw e;
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật lịch cho ăn: ", e);
            throw new RuntimeException("Không thể cập nhật lịch cho ăn: " + e.getMessage(), e);
        }
    }

    public void xoaLich(String id) {
        try {
            xoa(id);
        } catch (Exception e) {
            logger.error("Lỗi trong xoaLich(): ", e);
            throw e; // Re-throw để controller có thể handle
        }
    }

    @Transactional(readOnly = true)
    public List<LichChoAn> getLatest(int limit) {
        try {
            if (limit <= 0) {
                logger.warn("Limit không hợp lệ: {}, sử dụng mặc định 5", limit);
                limit = 5;
            }

            List<LichChoAn> result = repo.findTop5ByOrderByThoiGianDesc();
            logger.info("Lấy {} lịch cho ăn mới nhất thành công, số lượng thực: {}", limit, result.size());
            return result;
        } catch (Exception e) {
            logger.error("Lỗi khi lấy {} lịch cho ăn mới nhất: ", limit, e);
            return new ArrayList<>(); // Trả về danh sách rỗng thay vì crash
        }
    }
}
