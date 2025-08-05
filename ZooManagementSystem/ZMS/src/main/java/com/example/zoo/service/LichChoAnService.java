package com.example.zoo.service;

import com.example.zoo.model.LichChoAn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LichChoAnService {
    private final List<LichChoAn> danhSachLich = new ArrayList<>();

    public void themLich(LichChoAn lich) {
        danhSachLich.add(lich);
        System.out.println("✅ Thêm lịch cho ăn thành công");
    }

    public List<LichChoAn> layTatCa() {
        return danhSachLich;
    }

    public void capNhatLich(String maLich, LichChoAn lichMoi) {
        for (LichChoAn lich : danhSachLich) {
            if (lich.getMaLich().equals(maLich)) {
                lich.setDongVat(lichMoi.getDongVat());
                lich.setThucAn(lichMoi.getThucAn());
                lich.setNhanVien(lichMoi.getNhanVien());
                lich.setThoiGian(lichMoi.getThoiGian());
                System.out.println("✅ Cập nhật thành công.");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy lịch cho ăn cần sửa.");
    }

    public void xoaLich(String maLich) {
        danhSachLich.removeIf(lich -> lich.getMaLich().equals(maLich));
        System.out.println("✅ Đã xóa lịch nếu có.");
    }

    public LichChoAn timTheoMa(String maLich) {
        for (LichChoAn lich : danhSachLich) {
            if (lich.getMaLich().equals(maLich)) {
                return lich;
            }
        }
        return null;
    }
}
