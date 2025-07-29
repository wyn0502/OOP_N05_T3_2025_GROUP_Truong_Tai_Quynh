package com.example.zoo.manager;

import com.example.zoo.interfaces.IManager;
import com.example.zoo.model.DongVat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TRUONG
 */

@Service
public class DongVatManager implements IManager<DongVat> {

    private final List<DongVat> danhSachDongVat = new ArrayList<>();

    @Override
    public void them(DongVat dongVat) {
        danhSachDongVat.add(dongVat);
    }

    @Override
    public List<DongVat> hienThi() {
        return danhSachDongVat;
    }

    @Override
    public void sua(String ten, DongVat moi) {
        for (DongVat dv : danhSachDongVat) {
            if (dv.getTen().equalsIgnoreCase(ten)) {
                dv.setTen(moi.getTen());
                dv.setTuoi(moi.getTuoi());
                dv.setLoai(moi.getLoai());
                return;
            }
        }
    }

    @Override
    public void xoa(String ten) {
        danhSachDongVat.removeIf(dv -> dv.getTen().equalsIgnoreCase(ten));
    }

    public List<DongVat> layTatCa() {
        return danhSachDongVat;
    }
}
