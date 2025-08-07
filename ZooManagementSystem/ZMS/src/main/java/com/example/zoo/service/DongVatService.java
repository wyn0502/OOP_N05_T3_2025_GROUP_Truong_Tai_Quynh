package com.example.zoo.service;

import com.example.zoo.model.DongVat;
import com.example.zoo.repository.DongVatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DongVatService {

    @Autowired
    private DongVatRepository dongVatRepository;

    // Lấy tất cả động vật từ database
    public List<DongVat> layTatCa() {
        return dongVatRepository.findAll();
    }

    // Thêm hoặc cập nhật
    public void luuHoacCapNhat(DongVat dv) {
        dongVatRepository.save(dv);
    }

    // Tìm theo ID
    public DongVat timTheoId(Long id) {
        Optional<DongVat> optional = dongVatRepository.findById(id);
        return optional.orElse(null);
    }

    // Xóa theo ID
    public void xoaTheoId(Long id) {
        dongVatRepository.deleteById(id);
    }
}
