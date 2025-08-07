package com.example.zoo.service;

import com.example.zoo.model.DongVat;
import com.example.zoo.repository.DongVatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DongVatService {

    @Autowired
    private DongVatRepository dongVatRepository;

    public List<DongVat> layTatCa() {
        try {
            return dongVatRepository.findAll();
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy động vật: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void luuHoacCapNhat(DongVat dv) {
        try {
            dongVatRepository.save(dv);
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu động vật: " + e.getMessage());
            throw new RuntimeException("Không thể lưu động vật.", e);
        }
    }

    public DongVat timTheoId(Long id) {
        try {
            Optional<DongVat> optional = dongVatRepository.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm động vật: " + e.getMessage());
            return null;
        }
    }

    public void xoaTheoId(Long id) {
        try {
            dongVatRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa động vật: " + e.getMessage());
            throw new RuntimeException("Xóa động vật thất bại.", e);
        }
    }
}
