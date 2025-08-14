package com.example.zoo.service;

import com.example.zoo.interfaces.AbstractManager;
import com.example.zoo.model.Chuong;

import com.example.zoo.repository.ChuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChuongService extends AbstractManager<Chuong, String> {

    private final ChuongRepository repository;

    @Autowired
    public ChuongService(ChuongRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Chuong, String> getRepository() {
        return repository;
    }

    @Override
    protected String parseId(String idStr) {
        return idStr;
    }

    @Override
    public void them(Chuong c) {
        c.setSoLuongHienTai(0);
        if (c.getSucChuaToiDa() <= 0) {
            throw new IllegalArgumentException("Sức chứa tối đa phải lớn hơn 0");
        }
        repository.save(c);
        System.out.println("Đã thêm chuồng mới: " + c.getMaChuong());
    }

    @Override
    public void sua(String ma, Chuong newChuong) {
        repository.findById(ma).ifPresent(c -> {
            if (newChuong.getSucChuaToiDa() < c.getSoLuongHienTai()) {
                throw new IllegalArgumentException(
                    String.format("Sức chứa tối đa (%d) < số lượng hiện tại (%d)",
                        newChuong.getSucChuaToiDa(), c.getSoLuongHienTai())
                );
            }
            c.setTenKhuVuc(newChuong.getTenKhuVuc());
            c.setSucChuaToiDa(newChuong.getSucChuaToiDa());
            repository.save(c);
        });
    }

    public Chuong timTheoMa(String ma) { return repository.findById(ma).orElse(null); }

    public boolean chuongTonTai(String maChuong) { return repository.existsById(maChuong); }

    public long demTongSoChuong() { return repository.count(); }

    public List<Chuong> layChuongCoChoTrong() {
        return repository.findAll().stream()
            .filter(c -> c.getSoLuongHienTai() < c.getSucChuaToiDa())
            .toList();
    }

    public void capNhatSoLuongDongVat(String maChuong, int soLuongMoi) {
        repository.findById(maChuong).ifPresent(c -> {
            if (soLuongMoi < 0) throw new IllegalArgumentException("Số lượng không thể âm");
            if (soLuongMoi > c.getSucChuaToiDa())
                throw new IllegalArgumentException("Vượt sức chứa");
            c.setSoLuongHienTai(soLuongMoi);
            repository.save(c);
        });
    }
}
