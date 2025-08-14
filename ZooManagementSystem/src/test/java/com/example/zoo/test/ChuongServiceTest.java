package com.example.zoo.test;

import com.example.zoo.model.Chuong;
import com.example.zoo.service.ChuongService;

import com.example.zoo.repository.ChuongRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChuongServiceTest {

    @Mock
    private ChuongRepository repository;

    @InjectMocks
    private ChuongService service;

    @Test
    void testThemChuong() {
        Chuong c = new Chuong("C01", "Khu A", 10, 5);
        service.them(c);
        verify(repository).save(any(Chuong.class));
        assertEquals(0, c.getSoLuongHienTai()); // luôn set về 0 khi thêm
    }

    @Test
    void testHienThi() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Chuong("C01", "Khu A", 10, 0)));
        assertEquals(1, service.hienThi().size());
    }

    @Test
    void testSuaChuong() {
        Chuong existing = new Chuong("C02", "Khu B", 15, 4);
        when(repository.findById("C02")).thenReturn(Optional.of(existing));

        Chuong update = new Chuong("C02", "Khu BB", 20, 5);
        service.sua("C02", update);

        assertEquals("Khu BB", existing.getTenKhuVuc());
        assertEquals(20, existing.getSucChuaToiDa());
        verify(repository).save(existing);
    }

    @Test
    void testSuaChuong_VuotSucChua() {
        Chuong existing = new Chuong("C03", "Khu C", 10, 8);
        when(repository.findById("C03")).thenReturn(Optional.of(existing));

        Chuong update = new Chuong("C03", "Khu CC", 5, 8);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.sua("C03", update));
        assertTrue(ex.getMessage().contains("không thể nhỏ hơn"));
    }

    @Test
    void testCapNhatSoLuongDongVat() {
        Chuong existing = new Chuong("C04", "Khu D", 5, 2);
        when(repository.findById("C04")).thenReturn(Optional.of(existing));

        service.capNhatSoLuongDongVat("C04", 4);

        assertEquals(4, existing.getSoLuongHienTai());
        verify(repository).save(existing);
    }

    @Test
    void testCapNhatSoLuongDongVat_VuotSucChua() {
        Chuong existing = new Chuong("C05", "Khu E", 5, 2);
        when(repository.findById("C05")).thenReturn(Optional.of(existing));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.capNhatSoLuongDongVat("C05", 10));
        assertTrue(ex.getMessage().contains("vượt quá sức chứa"));
    }
}
