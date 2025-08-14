package com.example.zoo.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.zoo.model.DongVat;
import com.example.zoo.model.Chuong;

import static org.junit.jupiter.api.Assertions.*;

class DongVatTest {

    private DongVat dongVat;

    @BeforeEach
    void setUp() {
        dongVat = new DongVat("Sư tử", 5.5, "Thú săn mồi", "Khỏe mạnh", "C001");
    }

    @Test
    void testConstructor() {
        // Then
        assertEquals("Sư tử", dongVat.getTen());
        assertEquals(5.5, dongVat.getTuoi());
        assertEquals("Thú săn mồi", dongVat.getLoai());
        assertEquals("Khỏe mạnh", dongVat.getSucKhoe());
        assertEquals("C001", dongVat.getMaChuong());
    }

    @Test
    void testDefaultConstructor() {
        // When
        DongVat dongVatMoi = new DongVat();

        // Then
        assertNull(dongVatMoi.getTen());
        assertNull(dongVatMoi.getLoai());
        assertNull(dongVatMoi.getSucKhoe());
        assertNull(dongVatMoi.getMaChuong());
        assertEquals(0.0, dongVatMoi.getTuoi());
    }

    @Test
    void testSettersAndGetters() {
        // When
        dongVat.setId(1L);
        dongVat.setTen("Hổ Bengal");
        dongVat.setTuoi(4.2);
        dongVat.setLoai("Mèo lớn");
        dongVat.setSucKhoe("Bình thường");
        dongVat.setMaChuong("C002");

        // Then
        assertEquals(1L, dongVat.getId());
        assertEquals("Hổ Bengal", dongVat.getTen());
        assertEquals(4.2, dongVat.getTuoi());
        assertEquals("Mèo lớn", dongVat.getLoai());
        assertEquals("Bình thường", dongVat.getSucKhoe());
        assertEquals("C002", dongVat.getMaChuong());
    }

    @Test
    void testToString() {
        // Given
        dongVat.setId(1L);

        // When
        String result = dongVat.toString();

        // Then
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Sư tử"));
        assertTrue(result.contains("5.5"));
        assertTrue(result.contains("Thú săn mồi"));
        assertTrue(result.contains("Khỏe mạnh"));
        assertTrue(result.contains("C001"));
    }

    @Test
    void testChuongRelationship() {
        // Given
        Chuong chuong = new Chuong("C001", "Khu A", 10, 5);

        // When
        dongVat.setChuong(chuong);

        // Then
        assertEquals(chuong, dongVat.getChuong());
        assertEquals("C001", dongVat.getChuong().getMaChuong());
    }
}
