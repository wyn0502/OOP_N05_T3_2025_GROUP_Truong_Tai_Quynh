package com.example.zoo.test;

import com.example.zoo.model.DongVat;
import com.example.zoo.repository.DongVatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DongVatServiceTest {

    private DongVatRepository dongVatRepository;
    private DongVatService dongVatService;

    @BeforeEach
    void setUp() {
        dongVatRepository = mock(DongVatRepository.class);
        dongVatService = new DongVatService();
        dongVatService.dongVatRepository = dongVatRepository;
    }

    @Test
    void testLayTatCa() {
        DongVat dv = new DongVat("Hổ", 5, "Hổ", "Tốt", "Khu A");
        when(dongVatRepository.findAll()).thenReturn(Arrays.asList(dv));
        List<DongVat> list = dongVatService.layTatCa();
        assertEquals(1, list.size());
        assertEquals("Hổ", list.get(0).getTen());
    }

    @Test
    void testLuuHoacCapNhat() {
        DongVat dv = new DongVat("Gấu", 7, "Gấu", "Tốt", "Khu B");
        dongVatService.luuHoacCapNhat(dv);
        verify(dongVatRepository, times(1)).save(dv);
    }

    @Test
    void testTimTheoId() {
        DongVat dv = new DongVat("Chim", 2, "Chim", "Yếu", "Khu C");
        when(dongVatRepository.findById(1L)).thenReturn(Optional.of(dv));
        DongVat result = dongVatService.timTheoId(1L);
        assertNotNull(result);
        assertEquals("Chim", result.getTen());
    }

    @Test
    void testXoaTheoId() {
        dongVatService.xoaTheoId(10L);
        verify(dongVatRepository, times(1)).deleteById(10L);
    }

    @Test
    void testExceptionWhenSaveNull() {
        doThrow(new RuntimeException("Lỗi")).when(dongVatRepository).save(null);
        assertThrows(RuntimeException.class, () -> dongVatService.luuHoacCapNhat(null));
    }
}
