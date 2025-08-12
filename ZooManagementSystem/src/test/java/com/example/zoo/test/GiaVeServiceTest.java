package com.example.zoo.test;

import com.example.zoo.model.GiaVe;
import com.example.zoo.service.GiaVeService;
import com.example.zoo.repository.GiaVeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GiaVeServiceTest {

    @Mock
    private GiaVeRepository giaVeRepository;

    @InjectMocks
    private GiaVeService giaVeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLayTatCa() {
        // Arrange - Sử dụng constructor đúng
        GiaVe gv1 = new GiaVe("Người lớn", 100000.0, "Vé thường", 0.0);
        GiaVe gv2 = new GiaVe("Trẻ em", 50000.0, "Vé giảm giá", 50.0);
        when(giaVeRepository.findAll()).thenReturn(Arrays.asList(gv1, gv2));

        // Act
        List<GiaVe> result = giaVeService.layTatCa();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Người lớn", result.get(0).getLoaiVe());
        verify(giaVeRepository, times(1)).findAll();
    }

    @Test
    void testLuuGiaVe() {
        // Arrange
        GiaVe giaVe = new GiaVe("VIP", 200000.0, "Vé cao cấp", 10.0);

        // Act
        giaVeService.luu(giaVe);

        // Assert
        verify(giaVeRepository, times(1)).save(giaVe);
    }

    @Test
    void testTimTheoId_Found() {
        GiaVe giaVe = new GiaVe("Premium", 150000.0, "Vé đặc biệt", 5.0);
        when(giaVeRepository.findById(1L)).thenReturn(Optional.of(giaVe));

        GiaVe result = giaVeService.timTheoId(1L);

        assertNotNull(result);
        assertEquals("Premium", result.getLoaiVe());
    }

    @Test
    void testTimTheoId_NotFound() {
        when(giaVeRepository.findById(99L)).thenReturn(Optional.empty());

        GiaVe result = giaVeService.timTheoId(99L);

        assertNull(result);
    }

    @Test
    void testXoaTheoId() {
        giaVeService.xoaTheoId(1L);

        verify(giaVeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testLuuThrowsException() {
        doThrow(new RuntimeException("Database error")).when(giaVeRepository).save(any(GiaVe.class));

        assertThrows(RuntimeException.class, () -> {
            giaVeService.luu(new GiaVe());
        });
    }

    @Test
    void testTimKiem() {
        GiaVe gv1 = new GiaVe("Người lớn", 100000.0, "Vé thường", 0.0);
        GiaVe gv2 = new GiaVe("Trẻ em", 50000.0, "Vé giảm giá", 50.0);
        when(giaVeRepository.findAll()).thenReturn(Arrays.asList(gv1, gv2));

        List<GiaVe> result = giaVeService.timKiem("trẻ");

        assertEquals(1, result.size());
        assertEquals("Trẻ em", result.get(0).getLoaiVe());
    }
}
