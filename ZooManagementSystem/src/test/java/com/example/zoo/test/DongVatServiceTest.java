package com.example.zoo.test;

import com.example.zoo.model.Chuong;
import com.example.zoo.model.DongVat;
import com.example.zoo.repository.DongVatRepository;
import com.example.zoo.service.ChuongService;
import com.example.zoo.service.DongVatService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DongVatServiceTest {

    private DongVatRepository dongVatRepository;
    private ChuongService chuongService;
    private DongVatService dongVatService;

    @BeforeEach
    void setUp() {
        dongVatRepository = mock(DongVatRepository.class);
        chuongService = mock(ChuongService.class);
        dongVatService = new DongVatService();
        dongVatService.dongVatRepository = dongVatRepository;
        try {
            var field = DongVatService.class.getDeclaredField("chuongService");
            field.setAccessible(true);
            field.set(dongVatService, chuongService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLayTatCa() {
        DongVat dv = new DongVat("Hổ", 5, "Hổ", "Tốt", "C01");
        when(dongVatRepository.findAll()).thenReturn(List.of(dv));

        List<DongVat> list = dongVatService.layTatCa();
        assertEquals(1, list.size());
        assertEquals("Hổ", list.get(0).getTen());
    }

    @Test
    void testKiemTraSucChua_CoChoTrong() {
        Chuong chuong = new Chuong("C01", "Khu A", 5, 2);
        when(chuongService.timTheoMa("C01")).thenReturn(chuong);
        when(dongVatRepository.countByMaChuong("C01")).thenReturn(2L);

        boolean result = dongVatService.kiemTraSucChua("C01", null);
        assertTrue(result);
    }

    @Test
    void testLuuHoacCapNhat_VuotSucChua_ShouldThrow() {
        Chuong chuong = new Chuong("C01", "Khu A", 1, 1);
        when(chuongService.timTheoMa("C01")).thenReturn(chuong);
        when(dongVatRepository.countByMaChuong("C01")).thenReturn(1L);

        DongVat dv = new DongVat("Gấu", 4, "Gấu", "Tốt", "C01");
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> dongVatService.luuHoacCapNhat(dv));
        assertTrue(ex.getMessage().contains("đã đầy"));
    }

    @Test
    void testLuuHoacCapNhat_ThanhCong() {
        Chuong chuong = new Chuong("C01", "Khu A", 5, 1);
        when(chuongService.timTheoMa("C01")).thenReturn(chuong);
        when(dongVatRepository.countByMaChuong("C01")).thenReturn(1L);

        DongVat dv = new DongVat("Nai", 2, "Nai", "Tốt", "C01");
        dongVatService.luuHoacCapNhat(dv);
        verify(dongVatRepository).save(dv);
    }

    @Test
    void testTimTheoId() {
        DongVat dv = new DongVat("Chim", 2, "Chim", "Yếu", "C01");
        when(dongVatRepository.findById(1L)).thenReturn(Optional.of(dv));

        assertEquals("Chim", dongVatService.timTheoId(1L).getTen());
    }

    @Test
    void testXoaTheoId() {
        DongVat dv = new DongVat("Bò", 5, "Bò", "Tốt", "C01");
        when(dongVatRepository.findById(10L)).thenReturn(Optional.of(dv));

        dongVatService.xoaTheoId(10L);
        verify(dongVatRepository).deleteById(10L);
    }

    @Test
    void testDemDongVatTheoMaChuong() {
        when(dongVatRepository.countByMaChuong("C01")).thenReturn(3L);
        assertEquals(3, dongVatService.demDongVatTheoMaChuong("C01"));
    }

    @Test
    void testLayDongVatTheoMaChuong() {
        when(dongVatRepository.findByMaChuong("C01")).thenReturn(List.of(
                new DongVat("Khỉ", 3, "Khỉ", "Tốt", "C01")
        ));
        assertEquals(1, dongVatService.layDongVatTheoMaChuong("C01").size());
    }

    @Test
    void testLayDanhSachChuongCoChoTrong() {
        when(chuongService.layChuongCoChoTrong())
                .thenReturn(List.of(new Chuong("C01", "Khu A", 5, 2)));
        List<String> ds = dongVatService.layDanhSachChuongCoChoTrong();
        assertEquals("C01", ds.get(0));
    }

    @Test
    void testTimTheoLoai() {
        when(dongVatRepository.findByLoai("Hổ"))
                .thenReturn(List.of(new DongVat("Hổ", 5, "Hổ", "Tốt", "C01")));
        assertEquals(1, dongVatService.timTheoLoai("Hổ").size());
    }

    @Test
    void testTimTheoTenGanDung() {
        when(dongVatRepository.findByTenContainingIgnoreCase("ho"))
                .thenReturn(List.of(new DongVat("Hổ", 5, "Hổ", "Tốt", "C01")));
        assertEquals(1, dongVatService.timTheoTenGanDung("ho").size());
    }
}
