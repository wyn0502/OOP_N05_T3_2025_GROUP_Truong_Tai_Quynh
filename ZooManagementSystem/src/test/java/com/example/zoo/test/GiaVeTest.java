package com.example.zoo.test;

import com.example.zoo.model.GiaVe;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GiaVeTest {

    @Test
    void testApDungKhuyenMai_GiaTriHopLe() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaCoBan(100000.0); // Sử dụng method name đúng
        giaVe.setPhanTramGiamGia(20.0);
        
        // Test khuyến mãi 20% - method không tham số
        double ketQua = giaVe.apDungKhuyenMai();
        assertEquals(80000.0, ketQua, 0.01);
    }

    @Test
    void testApDungKhuyenMai_Bien0Phan100() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaCoBan(50000.0);
        
        // Test biên 0%
        giaVe.setPhanTramGiamGia(0.0);
        assertEquals(50000.0, giaVe.apDungKhuyenMai(), 0.01);
        
        // Test biên 100%
        giaVe.setPhanTramGiamGia(100.0);
        assertEquals(0.0, giaVe.apDungKhuyenMai(), 0.01);
    }

    @Test
    void testApDungKhuyenMai_GiaTriAm() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaCoBan(100000.0);
        giaVe.setPhanTramGiamGia(-10.0);
        
        // Test giá trị âm -> trả về giá gốc
        assertEquals(100000.0, giaVe.apDungKhuyenMai(), 0.01);
    }

    @Test
    void testApDungKhuyenMai_LonHon100() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaCoBan(100000.0);
        giaVe.setPhanTramGiamGia(150.0);
        
        // Test > 100% -> trả về giá gốc theo logic hiện tại
        assertEquals(100000.0, giaVe.apDungKhuyenMai(), 0.01);
    }

    @Test
    void testTinhGiaTheoDoiTuong_TreEm() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaCoBan(100000.0);
        
        double gia = giaVe.tinhGiaTheoDoiTuong("tre em");
        assertEquals(50000.0, gia, 0.01); // 50% giá gốc
    }

    @Test
    void testTinhGiaTheoDoiTuong_SinhVien() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaCoBan(100000.0);
        
        double gia = giaVe.tinhGiaTheoDoiTuong("sinh vien");
        assertEquals(70000.0, gia, 0.01); // 70% giá gốc
    }

    @Test
    void testTinhGiaTheoDoiTuong_NguoiGia() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaCoBan(100000.0);
        
        double gia = giaVe.tinhGiaTheoDoiTuong("nguoi gia");
        assertEquals(60000.0, gia, 0.01); // 60% giá gốc
    }

    @Test
    void testTinhGiaTheoDoiTuong_Default() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaCoBan(100000.0);
        
        // Test case không khớp -> trả về giá gốc
        assertEquals(100000.0, giaVe.tinhGiaTheoDoiTuong("nguoi lon"), 0.01);
        assertEquals(100000.0, giaVe.tinhGiaTheoDoiTuong(""), 0.01);
    }

    @Test
    void testTinhTongTien() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaCoBan(100000.0);
        
        // Test tính tổng tiền cho 3 vé trẻ em
        double tongTien = giaVe.tinhTongTien(3, "tre em");
        assertEquals(150000.0, tongTien, 0.01); // 3 * 50000
    }

    @Test
    void testConstructorVaGetter() {
        // Test constructor có tham số
        GiaVe giaVe = new GiaVe("Vé VIP", 200000.0, "Khuyến mãi", 15.0);
        
        assertEquals("Vé VIP", giaVe.getLoaiVe());
        assertEquals(200000.0, giaVe.getGiaCoBan());
        assertEquals("Khuyến mãi", giaVe.getLyDoGiamGia());
        assertEquals(15.0, giaVe.getPhanTramGiamGia());
    }

    @Test
    void testDefaultConstructor() {
        // Test constructor
        GiaVe giaVe = new GiaVe();
        
        assertEquals("", giaVe.getLoaiVe());
        assertEquals(100000.0, giaVe.getGiaCoBan());
        assertEquals("", giaVe.getLyDoGiamGia());
        assertEquals(0.0, giaVe.getPhanTramGiamGia());
    }
}
