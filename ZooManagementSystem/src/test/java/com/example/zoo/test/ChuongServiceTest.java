package com.example.zoo.test;

import com.example.zoo.model.Chuong;
import com.example.zoo.repository.ChuongRepository;
import com.example.zoo.service.ChuongService;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChuongServiceTest {

    @Mock
    private ChuongRepository chuongRepository;

    @InjectMocks
    private ChuongService chuongService;

    private Chuong chuongTest;

    @BeforeEach
    void setUp() {
        chuongTest = new Chuong("C001", "Khu A", 10, 0);
    }

    @Test
    void testThemChuong_ThanhCong() {
        // Given
        when(chuongRepository.save(any(Chuong.class))).thenReturn(chuongTest);

        // When
        chuongService.them(chuongTest);

        // Then
        verify(chuongRepository, times(1)).save(chuongTest);
        assertEquals(0, chuongTest.getSoLuongHienTai());
    }

    @Test
    void testThemChuong_SucChuaKhongHopLe() {
        // Given
        Chuong chuongKhongHopLe = new Chuong("C002", "Khu B", 0, 0);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            chuongService.them(chuongKhongHopLe);
        });
    }

    @Test
    void testSuaChuong_ThanhCong() {
        // Given
        Chuong chuongCu = new Chuong("C001", "Khu A", 10, 5);
        Chuong chuongMoi = new Chuong("C001", "Khu A Updated", 15, 5);
        
        when(chuongRepository.findById("C001")).thenReturn(Optional.of(chuongCu));
        when(chuongRepository.save(any(Chuong.class))).thenReturn(chuongCu);

        // When
        chuongService.sua("C001", chuongMoi);

        // Then
        verify(chuongRepository, times(1)).save(chuongCu);
        assertEquals("Khu A Updated", chuongCu.getTenKhuVuc());
        assertEquals(15, chuongCu.getSucChuaToiDa());
    }

    @Test
    void testSuaChuong_SucChuaNhoHonSoLuongHienTai() {
        // Given
        Chuong chuongCu = new Chuong("C001", "Khu A", 10, 8);
        Chuong chuongMoi = new Chuong("C001", "Khu A", 5, 8);
        
        when(chuongRepository.findById("C001")).thenReturn(Optional.of(chuongCu));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            chuongService.sua("C001", chuongMoi);
        });
    }

    @Test
    void testTimTheoMa_TimThay() {
        // Given
        when(chuongRepository.findById("C001")).thenReturn(Optional.of(chuongTest));

        // When
        Chuong result = chuongService.timTheoMa("C001");

        // Then
        assertNotNull(result);
        assertEquals("C001", result.getMaChuong());
        assertEquals("Khu A", result.getTenKhuVuc());
    }

    @Test
    void testTimTheoMa_KhongTimThay() {
        // Given
        when(chuongRepository.findById("C999")).thenReturn(Optional.empty());

        // When
        Chuong result = chuongService.timTheoMa("C999");

        // Then
        assertNull(result);
    }

    @Test
    void testLayChuongCoChoTrong() {
        // Given
        Chuong chuong1 = new Chuong("C001", "Khu A", 10, 5); // Còn chỗ
        Chuong chuong2 = new Chuong("C002", "Khu B", 5, 5);  // Hết chỗ
        Chuong chuong3 = new Chuong("C003", "Khu C", 8, 3);  // Còn chỗ
        
        when(chuongRepository.findAll()).thenReturn(Arrays.asList(chuong1, chuong2, chuong3));

        // When
        List<Chuong> result = chuongService.layChuongCoChoTrong();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getMaChuong().equals("C001")));
        assertTrue(result.stream().anyMatch(c -> c.getMaChuong().equals("C003")));
        assertFalse(result.stream().anyMatch(c -> c.getMaChuong().equals("C002")));
    }

    @Test
    void testCapNhatSoLuongDongVat_ThanhCong() {
        // Given
        when(chuongRepository.findById("C001")).thenReturn(Optional.of(chuongTest));
        when(chuongRepository.save(any(Chuong.class))).thenReturn(chuongTest);

        // When
        chuongService.capNhatSoLuongDongVat("C001", 7);

        // Then
        verify(chuongRepository, times(1)).save(chuongTest);
        assertEquals(7, chuongTest.getSoLuongHienTai());
    }

    @Test
    void testCapNhatSoLuongDongVat_SoLuongAm() {
        // Given
        when(chuongRepository.findById("C001")).thenReturn(Optional.of(chuongTest));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            chuongService.capNhatSoLuongDongVat("C001", -1);
        });
    }

    @Test
    void testCapNhatSoLuongDongVat_VuotSucChua() {
        // Given
        when(chuongRepository.findById("C001")).thenReturn(Optional.of(chuongTest));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            chuongService.capNhatSoLuongDongVat("C001", 15);
        });
    }
}
