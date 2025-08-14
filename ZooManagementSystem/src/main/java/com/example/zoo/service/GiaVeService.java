package com.example.zoo.service;

import com.example.zoo.interfaces.AbstractManager;
import com.example.zoo.model.GiaVe;
import com.example.zoo.repository.GiaVeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiaVeService extends AbstractManager<GiaVe, Long>{

    @Autowired
    private GiaVeRepository giaVeRepository;

    @Override
    protected JpaRepository<GiaVe, Long> getRepository() {
        return giaVeRepository;
    }

    public List<GiaVe> layTatCa() {
        return hienThi();
    }

    @Override
    protected Long parseId(String idStr) {
        try {
            return Long.valueOf(idStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID không hợp lệ: " + idStr, e);
        }
    }


    public GiaVe timTheoId(Long id) {
        try {
            return giaVeRepository.findById(id).orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm vé theo ID: " + e.getMessage());
            return null;
        }
    }

    public void luu(GiaVe ve) {
        try {
            giaVeRepository.save(ve);
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu vé: " + e.getMessage());
            throw new RuntimeException("Không thể lưu vé.", e);
        }
    }

    public void xoaTheoId(Long id) {
        try {
            giaVeRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa vé: " + e.getMessage());
            throw new RuntimeException("Xóa vé thất bại.", e);
        }
    }

    public List<GiaVe> timKiem(String keyword) {
        try {
            return giaVeRepository.findAll().stream()
                    .filter(ve -> ve.getLoaiVe().toLowerCase().contains(keyword.toLowerCase())
                            || (ve.getLyDoGiamGia() != null
                            && ve.getLyDoGiamGia().toLowerCase().contains(keyword.toLowerCase())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm vé: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
