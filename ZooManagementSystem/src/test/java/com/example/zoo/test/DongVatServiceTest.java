package com.example.zoo.test;

import com.example.zoo.model.Chuong;
import com.example.zoo.model.DongVat;
import com.example.zoo.service.ChuongService;
import com.example.zoo.service.DongVatService;

import com.example.zoo.repository.DongVatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DongVatServiceTest {

    @Mock
    private DongVatRepository dongVatRepository;

    @Mock
    private ChuongService chuongService;

    @InjectMocks
    private DongVatService dongVatService;

    private DongVat dongVatTest;
    private Chuong chuongTest;

    @BeforeEach
    void setUp() {
        dongVatTest = new DongVat("Sư tử", 5.0, "Thú săn mồi", "Khỏe mạnh", "C001");
        dongVatTest.setId(1L);
        
        chuongTest = new Chuong("C001", "Khu A", 10, 3);
    }

    @Test
    void testTimTheoId_TimThay() {
        // Given
        when(dongVatRepository.findById(1L)).thenReturn(Optional.of(dongVatTest));

        // When
        DongVat result = dongVatService.timTheoId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Sư tử", result.getTen());
    }

    @Test
    void testTimTheoId_KhongTimThay() {
        // Given
        when(dongVatRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        DongVat result = dongVatService.timTheoId(999L);

        // Then
        assertNull(result);
    }

    @Test
    void testKiemTraSucChua_ConChoTrong() {
        // Given
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);
        when(dongVatRepository.countByMaChuong("C001")).thenReturn(3L);

        // When
        boolean result = dongVatService.kiemTraSucChua("C001", null);

        // Then
        assertTrue(result);
    }

    @Test
    void testKiemTraSucChua_HetChoTrong() {
        // Given
        chuongTest.setSoLuongHienTai(10);
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);
        when(dongVatRepository.countByMaChuong("C001")).thenReturn(10L);

        // When
        boolean result = dongVatService.kiemTraSucChua("C001", null);

        // Then
        assertFalse(result);
    }

    @Test
    void testKiemTraSucChua_ChuongKhongTonTai() {
        // Given
        when(chuongService.timTheoMa("C999")).thenReturn(null);

        // When
        boolean result = dongVatService.kiemTraSucChua("C999", null);

        // Then
        assertFalse(result);
    }

    @Test
    void testLuuHoacCapNhat_ThemMoi_ThanhCong() {
        // Given
        DongVat dongVatMoi = new DongVat("Hổ", 3.0, "Thú săn mồi", "Khỏe mạnh", "C001");
        
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);
        when(dongVatRepository.countByMaChuong("C001")).thenReturn(3L);
        when(dongVatRepository.save(any(DongVat.class))).thenReturn(dongVatMoi);

        // When
        assertDoesNotThrow(() -> {
            dongVatService.luuHoacCapNhat(dongVatMoi);
        });

        // Then
        verify(dongVatRepository, times(1)).save(dongVatMoi);
        verify(chuongService, times(1)).capNhatSoLuongDongVat(eq("C001"), anyInt());
    }

    @Test
    void testLuuHoacCapNhat_ThemMoi_ChuongDay() {
        // Given
        DongVat dongVatMoi = new DongVat("Hổ", 3.0, "Thú săn mồi", "Khỏe mạnh", "C001");
        chuongTest.setSoLuongHienTai(10);
        
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);
        when(dongVatRepository.countByMaChuong("C001")).thenReturn(10L);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dongVatService.luuHoacCapNhat(dongVatMoi);
        });
        
        assertTrue(exception.getMessage().contains("đã đầy"));
    }

    @Test
    void testLuuHoacCapNhat_CapNhat_ChuyenChuong() {
        // Given
        DongVat dongVatCu = new DongVat("Sư tử", 5.0, "Thú săn mồi", "Khỏe mạnh", "C001");
        dongVatCu.setId(1L);
        
        DongVat dongVatMoi = new DongVat("Sư tử", 5.0, "Thú săn mồi", "Khỏe mạnh", "C002");
        dongVatMoi.setId(1L);
        
        Chuong chuong2 = new Chuong("C002", "Khu B", 5, 2);
        
        when(dongVatRepository.findById(1L)).thenReturn(Optional.of(dongVatCu));
        when(chuongService.timTheoMa("C002")).thenReturn(chuong2);
        when(dongVatRepository.countByMaChuong("C002")).thenReturn(2L);
        when(dongVatRepository.save(any(DongVat.class))).thenReturn(dongVatMoi);

        // When
        dongVatService.luuHoacCapNhat(dongVatMoi);

        // Then
        verify(dongVatRepository, times(1)).save(dongVatMoi);
        // Sửa: Sử dụng eq() cho cả 2 arguments
        verify(chuongService, times(1)).capNhatSoLuongDongVat(eq("C001"), anyInt()); // Chuồng cũ
        verify(chuongService, times(1)).capNhatSoLuongDongVat(eq("C002"), anyInt()); // Chuồng mới
    }

    @Test
    void testXoaTheoId_ThanhCong() {
        // Given
        when(dongVatRepository.findById(1L)).thenReturn(Optional.of(dongVatTest));
        doNothing().when(dongVatRepository).deleteById(1L);

        // When
        dongVatService.xoaTheoId(1L);

        // Then
        verify(dongVatRepository, times(1)).deleteById(1L);
        // Sửa: Sử dụng eq() cho String argument
        verify(chuongService, times(1)).capNhatSoLuongDongVat(eq("C001"), anyInt());
    }

    @Test
    void testDemDongVatTheoMaChuong() {
        // Given
        when(dongVatRepository.countByMaChuong("C001")).thenReturn(5L);

        // When
        long result = dongVatService.demDongVatTheoMaChuong("C001");

        // Then
        assertEquals(5L, result);
    }

    @Test
    void testLayDongVatTheoMaChuong() {
        // Given
        DongVat dongVat1 = new DongVat("Sư tử", 5.0, "Thú săn mồi", "Khỏe mạnh", "C001");
        DongVat dongVat2 = new DongVat("Hổ", 4.0, "Thú săn mồi", "Khỏe mạnh", "C001");
        List<DongVat> danhSach = Arrays.asList(dongVat1, dongVat2);
        
        when(dongVatRepository.findByMaChuong("C001")).thenReturn(danhSach);

        // When
        List<DongVat> result = dongVatService.layDongVatTheoMaChuong("C001");

        // Then
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dv -> dv.getTen().equals("Sư tử")));
        assertTrue(result.stream().anyMatch(dv -> dv.getTen().equals("Hổ")));
    }

    // Test thông qua các public method sử dụng parseId() thay vì test trực tiếp
    @Test
    void testSua_VoiIdHopLe() {
        // Given
        when(dongVatRepository.existsById(123L)).thenReturn(true);
        when(dongVatRepository.save(any(DongVat.class))).thenReturn(dongVatTest);

        // When & Then
        assertDoesNotThrow(() -> {
            dongVatService.sua("123", dongVatTest);
        });
        
        verify(dongVatRepository).save(dongVatTest);
    }

    @Test
    void testSua_VoiIdKhongHopLe() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            dongVatService.sua("abc", dongVatTest);
        });
    }
}
