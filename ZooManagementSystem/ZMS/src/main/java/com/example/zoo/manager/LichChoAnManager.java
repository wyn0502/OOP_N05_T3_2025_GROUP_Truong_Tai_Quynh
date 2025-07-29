package com.example.zoo.manager;

import com.example.zoo.interfaces.IManager;
import com.example.zoo.model.LichChoAn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LichChoAnManager implements IManager<LichChoAn> {
    private final List<LichChoAn> danhSachLich = new ArrayList<>();

    @Override
    public void them(LichChoAn lich) {
        danhSachLich.add(lich);
        System.out.println("✅ Thêm lịch cho ăn thành công");
    }

    @Override
    public List<LichChoAn> hienThi() {
        return danhSachLich;
    }

    @Override
    public void sua(String maLich, LichChoAn lichMoi) {
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

    @Override
    public void xoa(String maLich) {
        for (int i = 0; i < danhSachLich.size(); i++) {
            if (danhSachLich.get(i).getMaLich().equals(maLich)) {
                danhSachLich.remove(i);
                System.out.println("✅ Đã xóa lịch cho ăn.");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy lịch cho ăn.");
    }
}
