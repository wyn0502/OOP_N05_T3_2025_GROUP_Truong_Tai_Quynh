package com.example.zoo.service;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.interfaces.IManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LichChoAnService implements IManager<LichChoAn> {
    private List<LichChoAn> danhSachLich = new ArrayList<>();

    @Override
    public void them(LichChoAn lich) {
        danhSachLich.add(lich);
    }

    @Override
    public List<LichChoAn> hienThi() {
        return danhSachLich;
    }

    @Override
    public void sua(String id, LichChoAn lichMoi) {
        for (int i = 0; i < danhSachLich.size(); i++) {
            if (danhSachLich.get(i).getMaLich().equals(id)) {
                danhSachLich.set(i, lichMoi);
                return;
            }
        }
    }

    @Override
    public void xoa(String id) {
        danhSachLich.removeIf(lich -> lich.getMaLich().equals(id));
    }

    public List<LichChoAn> getAll() {
        return hienThi();
    }

    public void themLich(LichChoAn lich) {
        them(lich);
    }

    public LichChoAn timTheoId(String id) {
        for (LichChoAn lich : danhSachLich) {
            if (lich.getMaLich().equals(id)) {
                return lich;
            }
        }
        return null;
    }

    public void capNhatLich(LichChoAn lichMoi) {
        sua(lichMoi.getMaLich(), lichMoi);
    }

    public void xoaLich(String id) {
        xoa(id);
    }
}
