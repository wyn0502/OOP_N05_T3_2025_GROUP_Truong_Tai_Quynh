package com.example.zoo.service;

import com.example.zoo.model.GiaVe;
import com.example.zoo.repository.GiaVeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class GiaVeService {

    @Autowired
    private GiaVeRepository giaVeRepository;

    public List<GiaVe> layTatCa() {
        try {
            return giaVeRepository.findAll();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy tất cả vé: " + e.getMessage());
            return Collections.emptyList();
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

    public GiaVe timTheoId(Long id) {
        try {
            return giaVeRepository.findById(id).orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm vé theo ID: " + e.getMessage());
            return null;
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
                            || (ve.getLyDoGiamGia() != null && ve.getLyDoGiamGia().toLowerCase().contains(keyword.toLowerCase())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm kiếm vé: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
