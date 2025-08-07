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
        repository.save(c);
    }

    public List<Chuong> hienThi() {
        return repository.findAll();
    }

    public void sua(String ma, Chuong newChuong) {
        repository.findById(ma).ifPresent(c -> {
            c.setTenKhuVuc(newChuong.getTenKhuVuc());
            c.setSucChuaToiDa(newChuong.getSucChuaToiDa());
            c.setSoLuongHienTai(newChuong.getSoLuongHienTai());
            repository.save(c);
        });
    }

    public void xoa(String ma) {
        repository.deleteById(ma);
    }

    public Chuong timTheoMa(String ma) {
        return repository.findById(ma).orElse(null);
    }
}
