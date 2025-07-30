package com.example.zoo.service;

import com.example.zoo.model.GiaVe;
import com.example.zoo.interfaces.IManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiaVeService implements IManager<GiaVe> {
    private List<GiaVe> danhSachVe = new ArrayList<>();

    @Override
    public void them(GiaVe ve) {
        danhSachVe.add(ve);
    }

    @Override
    public List<GiaVe> hienThi() {
        return danhSachVe;
    }

    @Override
    public void sua(String id, GiaVe veMoi) {
        for (int i = 0; i < danhSachVe.size(); i++) {
            if (danhSachVe.get(i).getId().equals(id)) {
                danhSachVe.set(i, veMoi);
                return;
            }
        }
    }

    @Override
    public void xoa(String id) {
        danhSachVe.removeIf(ve -> ve.getId().equals(id));
    }

    public List<GiaVe> getAll() {
        return hienThi();
    }

    public void themVe(GiaVe ve) {
        them(ve);
    }

    public GiaVe timTheoId(String id) {
        for (GiaVe ve : danhSachVe) {
            if (ve.getId().equals(id)) {
                return ve;
            }
        }
        return null;
    }

    public void capNhatVe(GiaVe veMoi) {
        sua(veMoi.getId(), veMoi);
    }

    public void xoaVe(String id) {
        xoa(id);
    }
}
