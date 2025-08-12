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
        // Arrange
        GiaVe gv1 = new GiaVe("Người lớn", 100000, "Vé thường");
        GiaVe gv2 = new GiaVe("Trẻ em", 50000, "Vé giảm giá");
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
        GiaVe giaVe = new GiaVe("VIP", 200000, "Vé cao cấp");

        // Act
        giaVeService.luu(giaVe);

        // Assert
        verify(giaVeRepository, times(1)).save(giaVe);
    }

    @Test
    void testTimTheoId_Found() {
        // Arrange
        GiaVe giaVe = new GiaVe("Premium", 150000, "Vé đặc biệt");
        when(giaVeRepository.findById(1L)).thenReturn(Optional.of(giaVe));

        // Act
        GiaVe result = giaVeService.timTheoId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Premium", result.getLoaiVe());
    }

    @Test
    void testTimTheoId_NotFound() {
        // Arrange
        when(giaVeRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        GiaVe result = giaVeService.timTheoId(99L);

        // Assert
        assertNull(result);
    }

    @Test
    void testXoa() {
        // Act
        giaVeService.xoa(1L);

        // Assert
        verify(giaVeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testLuuThrowsException() {
        // Arrange
        doThrow(new RuntimeException("Database error")).when(giaVeRepository).save(any(GiaVe.class));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            giaVeService.luu(new GiaVe());
        });
    }
}
