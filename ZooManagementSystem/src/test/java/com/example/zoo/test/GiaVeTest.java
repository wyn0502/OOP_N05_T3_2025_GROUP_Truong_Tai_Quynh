package com.example.zoo.test;

import com.example.zoo.model.GiaVe;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GiaVeTest {

    @Test
    void testApDungKhuyenMai_GiaTriHopLe() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaGoc(100000);
        
        // Test khuyến mãi 20%
        double ketQua = giaVe.apDungKhuyenMai(20);
        assertEquals(80000, ketQua, 0.01);
    }

    @Test
    void testApDungKhuyenMai_Bien0Phan100() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaGoc(50000);
        
        // Test biên 0%
        assertEquals(50000, giaVe.apDungKhuyenMai(0), 0.01);
        
        // Test biên 100%
        assertEquals(0, giaVe.apDungKhuyenMai(100), 0.01);
    }

    @Test
    void testApDungKhuyenMai_GiaTriAm() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaGoc(100000);
        
        // Test giá trị âm -> trả về giá gốc
        assertEquals(100000, giaVe.apDungKhuyenMai(-10), 0.01);
    }

    @Test
    void testApDungKhuyenMai_LonHon100() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaGoc(100000);
        
        // Test > 100% -> trả về 0
        assertEquals(0, giaVe.apDungKhuyenMai(150), 0.01);
    }

    @Test
    void testTinhGiaTheoDoiTuong_TreEm() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaGoc(100000);
        
        double gia = giaVe.tinhGiaTheoDoiTuong("tre em");
        assertEquals(50000, gia, 0.01); // 50% giá gốc
    }

    @Test
    void testTinhGiaTheoDoiTuong_SinhVien() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaGoc(100000);
        
        double gia = giaVe.tinhGiaTheoDoiTuong("sinh vien");
        assertEquals(70000, gia, 0.01); // 70% giá gốc
    }

    @Test
    void testTinhGiaTheoDoiTuong_NguoiGia() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaGoc(100000);
        
        double gia = giaVe.tinhGiaTheoDoiTuong("nguoi gia");
        assertEquals(60000, gia, 0.01); // 60% giá gốc
    }

    @Test
    void testTinhGiaTheoDoiTuong_Default() {
        GiaVe giaVe = new GiaVe();
        giaVe.setGiaGoc(100000);
        
        // Test case không khớp -> trả về giá gốc
        assertEquals(100000, giaVe.tinhGiaTheoDoiTuong("nguoi lon"), 0.01);
        assertEquals(100000, giaVe.tinhGiaTheoDoiTuong(""), 0.01);
        assertEquals(100000, giaVe.tinhGiaTheoDoiTuong(null), 0.01);
    }
}
