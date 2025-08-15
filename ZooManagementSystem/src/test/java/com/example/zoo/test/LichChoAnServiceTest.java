package com.example.zoo.test;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.service.LichChoAnService;
import com.example.zoo.repository.LichChoAnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LichChoAnServiceTest {

    @Mock
    private LichChoAnRepository lichChoAnRepository;

    @InjectMocks
    private LichChoAnService lichChoAnService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        // Arrange
        LichChoAn lich1 = new LichChoAn("L001", "Hổ", "Thịt", "Nhân viên A", LocalDateTime.now());
        LichChoAn lich2 = new LichChoAn("L002", "Sư tử", "Cá", "Nhân viên B", LocalDateTime.now());
        when(lichChoAnRepository.findAllByOrderByThoiGianDesc()).thenReturn(Arrays.asList(lich1, lich2));

        // Act
        List<LichChoAn> result = lichChoAnService.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("L001", result.get(0).getMaLich());
        verify(lichChoAnRepository, times(1)).findAllByOrderByThoiGianDesc();
    }

    @Test
    void testThemLich() {
        // Arrange
        LichChoAn lich = new LichChoAn("L003", "Voi", "Lá", "Nhân viên C", LocalDateTime.now());

        // Act
        lichChoAnService.themLich(lich);

        // Assert
        verify(lichChoAnRepository, times(1)).save(lich);
    }

    @Test
    void testTimTheoId_Found() {
        LichChoAn lich = new LichChoAn("L004", "Gấu", "Mật", "Nhân viên D", LocalDateTime.now());
        when(lichChoAnRepository.findById("L004")).thenReturn(Optional.of(lich));

        LichChoAn result = lichChoAnService.timTheoId("L004");

        assertNotNull(result);
        assertEquals("L004", result.getMaLich());
    }

    @Test
    void testTimTheoId_NotFound() {
        when(lichChoAnRepository.findById("L999")).thenReturn(Optional.empty());

        LichChoAn result = lichChoAnService.timTheoId("L999");

        assertNull(result);
    }

    @Test
    void testXoaLich() {
        when(lichChoAnRepository.existsById("L001")).thenReturn(true);

        lichChoAnService.xoaLich("L001");

        verify(lichChoAnRepository, times(1)).deleteById("L001");
    }

    @Test
    void testCapNhatLich() {
        LichChoAn lich = new LichChoAn("L005", "Khỉ", "Chuối", "Nhân viên E", LocalDateTime.now());

        lichChoAnService.capNhatLich(lich);

        verify(lichChoAnRepository, times(1)).save(lich);
    }

    @Test
    void testGetLatest() {
        LichChoAn lich1 = new LichChoAn("L006", "Chim", "Hạt", "Nhân viên F", LocalDateTime.now());
        when(lichChoAnRepository.findTop5ByOrderByThoiGianDesc()).thenReturn(Arrays.asList(lich1));

        List<LichChoAn> result = lichChoAnService.getLatest(5);

        assertEquals(1, result.size());
        assertEquals("L006", result.get(0).getMaLich());
    }
}
