package com.example.zoo.test;

import com.example.zoo.model.Chuong;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ChuongTest {

    @Test
    void testCoChoTrong_True() {
        Chuong c = new Chuong("C01", "Khu A", 10, 5);
        assertTrue(c.coChoTrong());
        assertEquals(5, c.soChoTrong());
    }

    @Test
    void testCoChoTrong_False() {
        Chuong c = new Chuong("C02", "Khu B", 5, 5);
        assertFalse(c.coChoTrong());
        assertEquals(0, c.soChoTrong());
    }

    @Test
    void testValidateChuong_SoLuongLonHonSucChua() throws Exception {
        Chuong c = new Chuong("C03", "Khu C", 5, 10);

        Method m = Chuong.class.getDeclaredMethod("validateChuong");
        m.setAccessible(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            try {
                m.invoke(c);
            } catch (InvocationTargetException e) {
                throw (RuntimeException) e.getCause();
            }
        });

        assertTrue(ex.getMessage().contains("không thể lớn hơn"));
    }

    @Test
    void testValidateChuong_SucChuaKhongHopLe() throws Exception {
        Chuong c = new Chuong("C04", "Khu D", 0, 0);

        Method m = Chuong.class.getDeclaredMethod("validateChuong");
        m.setAccessible(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            try {
                m.invoke(c);
            } catch (InvocationTargetException e) {
                throw (RuntimeException) e.getCause();
            }
        });

        assertEquals("Sức chứa tối đa phải lớn hơn 0", ex.getMessage());
    }

    @Test
    void testToString() {
        Chuong c = new Chuong("C05", "Khu E", 8, 2);
        String str = c.toString();
        assertTrue(str.contains("C05"));
        assertTrue(str.contains("Khu E"));
    }
}
