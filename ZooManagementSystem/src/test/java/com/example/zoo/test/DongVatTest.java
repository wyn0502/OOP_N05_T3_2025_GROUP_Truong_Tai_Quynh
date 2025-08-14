package com.example.zoo.test;

import com.example.zoo.model.DongVat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DongVatTest {

    @Test
    void testConstructorAndGettersSetters() {
        DongVat dv = new DongVat("Khỉ", 4, "Linh trưởng", "Tốt", "C01");

        assertEquals("Khỉ", dv.getTen());
        assertEquals(4, dv.getTuoi());
        assertEquals("Linh trưởng", dv.getLoai());
        assertEquals("Tốt", dv.getSucKhoe());
        assertEquals("C01", dv.getMaChuong());

        dv.setId(100L);
        assertEquals(100L, dv.getId());
    }

    @Test
    void testToStringContainsFields() {
        DongVat dv = new DongVat("Hổ", 7, "Hổ", "Tốt", "C02");
        String str = dv.toString();
        assertTrue(str.contains("Hổ"));
        assertTrue(str.contains("Tốt"));
        assertTrue(str.contains("C02"));
    }
}
