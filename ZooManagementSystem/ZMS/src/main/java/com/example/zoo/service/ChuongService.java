package com.example.zoo.service;

import com.example.zoo.model.Chuong;
import com.example.zoo.interfaces.IManager;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChuongService implements IManager<Chuong> {
    private final List<Chuong> danhSachChuong = new ArrayList<>();

    @Override
    public void them(Chuong c) { danhSachChuong.add(c); }

    @Override
    public List<Chuong> hienThi() { return danhSachChuong; }

    @Override
    public void sua(String ma, Chuong newChuong) {
        for (Chuong c : danhSachChuong) {
            if (c.getMaChuong().equals(ma)) {
                if (newChuong.getTenKhuVuc() != null)
                    c.setTenKhuVuc(newChuong.getTenKhuVuc());
                if (newChuong.getSucChuaToiDa() != 0)
                    c.setSucChuaToiDa(newChuong.getSucChuaToiDa());
                if (newChuong.getSoLuongHienTai() != 0)
                    c.setSoLuongHienTai(newChuong.getSoLuongHienTai());
                return;
            }
        }
    }

    @Override
    public void xoa(String ma) {
        danhSachChuong.removeIf(c -> c.getMaChuong().equals(ma));
    }
}
