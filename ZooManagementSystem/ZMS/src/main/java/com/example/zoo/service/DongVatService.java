package com.example.zoo.service;

import com.example.zoo.interfaces.IManager;
import com.example.zoo.model.DongVat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TRUONG
 */

@Service
public class DongVatService {
    private final List<DongVat> danhSach = new ArrayList<>();

    public void them(DongVat dv) {
        xoa(dv.getTen());
        danhSach.add(dv);
    }

    public List<DongVat> layTatCa() {
        return danhSach;
    }

    public void xoa(String ten) {
        danhSach.removeIf(dv -> dv.getTen().equalsIgnoreCase(ten));
    }

    public DongVat timTheoTen(String ten) {
        return danhSach.stream()
                .filter(dv -> dv.getTen().equalsIgnoreCase(ten))
                .findFirst()
                .orElse(null);
    }
}
