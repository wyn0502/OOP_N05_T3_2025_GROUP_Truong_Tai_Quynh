package com.example.zoo.test;

import com.example.zoo.model.LichChoAn;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class LichChoAnTest {

    @Test
    void testConstructorVaGetter() {
        // Test constructor có tham số
        String maLich = "L001";
        String dongVat = "Hổ Bengal";
        String thucAn = "Thịt tươi";
        String nhanVien = "Nguyễn Văn A";
        LocalDateTime thoiGian = LocalDateTime.now();
        
        LichChoAn lich = new LichChoAn(maLich, dongVat, thucAn, nhanVien, thoiGian);
        
        assertEquals("L001", lich.getMaLich());
        assertEquals("Hổ Bengal", lich.getDongVat());
        assertEquals("Thịt tươi", lich.getThucAn());
        assertEquals("Nguyễn Văn A", lich.getNhanVien());
        assertEquals(thoiGian, lich.getThoiGian());
    }

    @Test
    void testDefaultConstructor() {
        // Test constructor mặc định
        LichChoAn lich = new LichChoAn();
        
        assertNull(lich.getMaLich());
        assertNull(lich.getDongVat());
        assertNull(lich.getThucAn());
        assertNull(lich.getNhanVien());
        assertNull(lich.getThoiGian());
    }

    @Test
    void testSetterGetter() {
        LichChoAn lich = new LichChoAn();
        
        lich.setMaLich("L002");
        lich.setDongVatId(1L);
        lich.setNhanVienId(2L);
        lich.setDongVat("Sư tử");
        lich.setThucAn("Cá tươi");
        lich.setNhanVien("Trần Thị B");
        
        assertEquals("L002", lich.getMaLich());
        assertEquals(Long.valueOf(1L), lich.getDongVatId());
        assertEquals(Long.valueOf(2L), lich.getNhanVienId());
        assertEquals("Sư tử", lich.getDongVat());
        assertEquals("Cá tươi", lich.getThucAn());
        assertEquals("Trần Thị B", lich.getNhanVien());
    }

    @Test
    void testGetId() {
        LichChoAn lich = new LichChoAn();
        lich.setMaLich("L003");
        
        assertEquals("L003", lich.getId());
    }

    @Test
    void testFullConstructor() {
        // Test constructor đầy đủ
        LichChoAn lich = new LichChoAn("L004", 1L, 2L, "Voi", "Lá cây", "Lê Văn C", LocalDateTime.now());
        
        assertEquals("L004", lich.getMaLich());
        assertEquals(Long.valueOf(1L), lich.getDongVatId());
        assertEquals(Long.valueOf(2L), lich.getNhanVienId());
        assertEquals("Voi", lich.getDongVat());
        assertEquals("Lá cây", lich.getThucAn());
        assertEquals("Lê Văn C", lich.getNhanVien());
        assertNotNull(lich.getThoiGian());
    }
}
