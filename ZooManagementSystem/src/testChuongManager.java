package src;

import src.Chuong;

public class testChuongManager {
    public void chayThu() {
        ChuongManager manager = new ChuongManager();

        manager.danhSachChuong.add(new Chuong("C1", "Khu A", 10, 5));
        manager.danhSachChuong.add(new Chuong("C2", "Khu B", 20, 15));
        manager.danhSachChuong.add(new Chuong("C3", "Khu C", 12, 6));

        manager.hienThiTatCa();

        manager.suaChuong();
        manager.xoaChuong();
    }
}


