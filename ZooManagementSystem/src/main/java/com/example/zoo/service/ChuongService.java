package com.example.zoo.service;

import com.example.zoo.model.Chuong;
import com.example.zoo.repository.ChuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChuongService {

    private final ChuongRepository repository;

    @Autowired
    public ChuongService(ChuongRepository repository) {
        this.repository = repository;
    }

    public void them(Chuong c) {
        try {
            repository.save(c);
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm chuồng: " + e.getMessage());
            throw new RuntimeException("Không thể thêm chuồng.", e);
        }
    }

    public List<Chuong> hienThi() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách chuồng: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public void sua(String ma, Chuong newChuong) {
        try {
            repository.findById(ma).ifPresent(c -> {
                c.setTenKhuVuc(newChuong.getTenKhuVuc());
                c.setSucChuaToiDa(newChuong.getSucChuaToiDa());
                c.setSoLuongHienTai(newChuong.getSoLuongHienTai());
                repository.save(c);
            });
        } catch (Exception e) {
            System.err.println("Lỗi khi sửa chuồng: " + e.getMessage());
            throw new RuntimeException("Không thể cập nhật chuồng.", e);
        }
    }

    public void xoa(String ma) {
        try {
            repository.deleteById(ma);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa chuồng: " + e.getMessage());
            throw new RuntimeException("Không thể xóa chuồng.", e);
        }
    }

    public Chuong timTheoMa(String ma) {
        try {
            return repository.findById(ma).orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm chuồng: " + e.getMessage());
            return null;
        }
    }

    public boolean chuongTonTai(String maChuong) {
        try {
            return repository.existsById(maChuong);
        } catch (Exception e) {
            System.err.println("Lỗi khi kiểm tra chuồng: " + e.getMessage());
            return false;
        }
    }

    public long demTongSoChuong() {
        try {
            return repository.count();
        } catch (Exception e) {
            System.err.println("Lỗi khi đếm chuồng: " + e.getMessage());
            return 0;
        }
    }

    public List<Chuong> layChuongCoChoTrong() {
        try {
            return repository.findAll().stream()
                .filter(c -> c.getSoLuongHienTai() < c.getSucChuaToiDa())
                .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy chuồng có chỗ trống: " + e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public void capNhatSoLuongDongVat(String maChuong, int soLuongMoi) {
        try {
            repository.findById(maChuong).ifPresent(c -> {
                c.setSoLuongHienTai(soLuongMoi);
                repository.save(c);
            });
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật số lượng động vật: " + e.getMessage());
            throw new RuntimeException("Không thể cập nhật số lượng động vật trong chuồng.", e);
        }
    }
}
