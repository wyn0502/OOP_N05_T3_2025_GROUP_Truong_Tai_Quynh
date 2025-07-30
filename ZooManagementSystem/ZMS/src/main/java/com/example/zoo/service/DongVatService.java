package com.example.zoo.service;

import com.example.zoo.model.DongVat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DongVatService {
    private final List<DongVat> danhSach = new ArrayList<>();

    public void them(DongVat dv) {
        danhSach.add(dv);
    }

    public void sua(String tenGoc, DongVat dongVatMoi) {
        xoa(tenGoc);
        them(dongVatMoi);
    }

    public List<DongVat> layTatCa() {
        return danhSach;
    }

    public boolean xoa(String ten) {
        return danhSach.removeIf(dv -> dv.getTen().equalsIgnoreCase(ten));
    }

    public DongVat timTheoTen(String ten) {
        return danhSach.stream()
                .filter(dv -> dv.getTen().equalsIgnoreCase(ten))
                .findFirst()
                .orElse(null);
    }
}