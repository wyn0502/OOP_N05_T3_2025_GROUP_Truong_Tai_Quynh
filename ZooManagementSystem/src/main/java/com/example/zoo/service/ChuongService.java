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
            c.setSoLuongHienTai(0);
            
            if (c.getSucChuaToiDa() <= 0) {
                throw new IllegalArgumentException("Sức chứa tối đa phải lớn hơn 0");
            }
            
            repository.save(c);
            System.out.println("Đã thêm chuồng mới: " + c.getMaChuong() + " với 0 động vật");
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
                if (newChuong.getSucChuaToiDa() < c.getSoLuongHienTai()) {
                    throw new IllegalArgumentException(
                        String.format("Sức chứa tối đa (%d) không thể nhỏ hơn số lượng động vật hiện tại (%d)", 
                                     newChuong.getSucChuaToiDa(), c.getSoLuongHienTai()));
                }
                
                repository.save(c);
                System.out.println("Đã cập nhật chuồng: " + ma + 
                                 " - Sức chứa: " + c.getSucChuaToiDa() + 
                                 " - Hiện tại: " + c.getSoLuongHienTai());
            });
        } catch (Exception e) {
            System.err.println("Lỗi khi sửa chuồng: " + e.getMessage());
            throw new RuntimeException("Không thể cập nhật chuồng: " + e.getMessage(), e);
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
                if (soLuongMoi < 0) {
                    throw new IllegalArgumentException("Số lượng động vật không thể âm");
                }
                if (soLuongMoi > c.getSucChuaToiDa()) {
                    throw new IllegalArgumentException(
                        String.format("Số lượng động vật (%d) vượt quá sức chứa tối đa (%d)", 
                                     soLuongMoi, c.getSucChuaToiDa()));
                }
                
                c.setSoLuongHienTai(soLuongMoi);
                repository.save(c);
                System.out.println("Đã cập nhật số lượng động vật trong chuồng " + 
                                 maChuong + ": " + soLuongMoi);
            });
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật số lượng động vật: " + e.getMessage());
            throw new RuntimeException("Không thể cập nhật số lượng động vật trong chuồng: " + e.getMessage(), e);
        }
    }
}
