package com.example.zoo.service;

import com.example.zoo.interfaces.IManager;
import com.example.zoo.model.LichChoAn;
import com.example.zoo.repository.LichChoAnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LichChoAnService implements IManager<LichChoAn> {

    private final LichChoAnRepository repo;

    public LichChoAnService(LichChoAnRepository repo) {
        this.repo = repo;
    }

    // ===== IManager impl =====
    @Override
    public void them(LichChoAn lich) {
        repo.save(lich);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LichChoAn> hienThi() {
        return repo.findAllByOrderByThoiGianDesc();
    }

    @Override
    public void sua(String id, LichChoAn lichMoi) {
        if (repo.existsById(id)) {
           
            lichMoi.setMaLich(id);
            repo.save(lichMoi);
        }
    }

    @Override
    public void xoa(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    // ===== API dùng trong controller =====
    @Transactional(readOnly = true)
    public List<LichChoAn> getAll() {
        return hienThi();
    }

    public void themLich(LichChoAn lich) {
        them(lich);
    }

    @Transactional(readOnly = true)
    public LichChoAn timTheoId(String id) {
        return repo.findById(id).orElse(null);
    }

    public void capNhatLich(LichChoAn lichMoi) {
        repo.save(lichMoi);
    }

    public void xoaLich(String id) {
        xoa(id);
    }

    @Transactional(readOnly = true)
    public List<LichChoAn> getLatest(int limit) {
       
        return repo.findTop5ByOrderByThoiGianDesc();
    }
}
