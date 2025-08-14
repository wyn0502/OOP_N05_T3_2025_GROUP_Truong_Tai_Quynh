package com.example.zoo.test;

import com.example.zoo.model.Chuong;
import com.example.zoo.model.DongVat;
import com.example.zoo.model.User;
import com.example.zoo.service.ChuongService;
import com.example.zoo.service.DongVatService;
import com.example.zoo.controller.DongVatController;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DongVatControllerTest {

    @Mock
    private DongVatService dongVatService;

    @Mock
    private ChuongService chuongService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private DongVatController dongVatController;

    private User adminUser;
    private User staffUser;
    private User guestUser;
    private DongVat dongVatTest;
    private Chuong chuongTest;

    @BeforeEach
    void setUp() {
        adminUser = new User();
        adminUser.setRole("admin");
        
        staffUser = new User();
        staffUser.setRole("staff");
        
        guestUser = new User();
        guestUser.setRole("guest");
        
        dongVatTest = new DongVat("Sư tử", 5.0, "Thú săn mồi", "Khỏe mạnh", "C001");
        dongVatTest.setId(1L);
        
        chuongTest = new Chuong("C001", "Khu A", 10, 5);
    }

    @Test
    void testHienThiDanhSach_Admin_ThanhCong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.layTatCa()).thenReturn(Arrays.asList(dongVatTest));

        // When
        String result = dongVatController.hienThiDanhSach(model, session, null);

        // Then
        assertEquals("dongvat/list", result);
        verify(model).addAttribute("danhSach", Arrays.asList(dongVatTest));
        verify(model).addAttribute("role", "admin");
    }

    @Test
    void testHienThiDanhSach_Staff_ThanhCong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);
        when(dongVatService.layTatCa()).thenReturn(Arrays.asList(dongVatTest));

        // When
        String result = dongVatController.hienThiDanhSach(model, session, null);

        // Then
        assertEquals("dongvat/list", result);
        verify(model).addAttribute("danhSach", Arrays.asList(dongVatTest));
        verify(model).addAttribute("role", "staff");
    }

    @Test
    void testHienThiDanhSach_Guest_KhongCoQuyen() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(guestUser);

        // When
        String result = dongVatController.hienThiDanhSach(model, session, null);

        // Then
        assertEquals("redirect:/error/505", result);
    }

    @Test
    void testHienThiDanhSach_TimKiem_TimThay() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.timTheoId(1L)).thenReturn(dongVatTest);

        // When
        String result = dongVatController.hienThiDanhSach(model, session, "1");

        // Then
        assertEquals("dongvat/list", result);
        verify(model).addAttribute("danhSach", Arrays.asList(dongVatTest));
        verify(model).addAttribute("searchResult", "Tìm thấy 1 kết quả cho ID: 1");
        verify(model).addAttribute("searchValue", "1");
    }

    @Test
    void testHienThiDanhSach_TimKiem_KhongTimThay() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.timTheoId(999L)).thenReturn(null);

        // When
        String result = dongVatController.hienThiDanhSach(model, session, "999");

        // Then
        assertEquals("dongvat/list", result);
        verify(model).addAttribute("danhSach", Collections.emptyList());
        verify(model).addAttribute("searchResult", "Không tìm thấy động vật với ID: 999");
    }

    @Test
    void testHienThiDanhSach_TimKiem_IdKhongHopLe() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);

        // When
        String result = dongVatController.hienThiDanhSach(model, session, "abc");

        // Then
        assertEquals("dongvat/list", result);
        verify(model).addAttribute("danhSach", Collections.emptyList());
        verify(model).addAttribute("searchResult", "ID phải là một số nguyên hợp lệ");
    }

    @Test
    void testHienThiFormThem_Admin_CoChuongTrong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.layChuongCoChoTrong()).thenReturn(Arrays.asList(chuongTest));

        // When
        String result = dongVatController.hienThiFormThem(model, session);

        // Then
        assertEquals("dongvat/form", result);
        verify(model).addAttribute(eq("dongVat"), any(DongVat.class));
        verify(model).addAttribute("danhSachChuong", Arrays.asList(chuongTest));
    }

    @Test
    void testHienThiFormThem_Admin_KhongCoChuongTrong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.layChuongCoChoTrong()).thenReturn(Collections.emptyList());

        // When
        String result = dongVatController.hienThiFormThem(model, session);

        // Then
        assertEquals("dongvat/form", result);
        verify(model).addAttribute("warning", "Hiện tại không có chuồng nào còn chỗ trống! Vui lòng tạo mới hoặc mở rộng.");
    }

    @Test
    void testHienThiFormThem_Staff_KhongCoQuyen() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);

        // When
        String result = dongVatController.hienThiFormThem(model, session);

        // Then
        assertEquals("redirect:/error/505", result);
    }

    @Test
    void testHienThiFormSua_Admin_TimThay() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.timTheoId(1L)).thenReturn(dongVatTest);
        when(chuongService.layChuongCoChoTrong()).thenReturn(Arrays.asList(chuongTest));
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);

        // When
        String result = dongVatController.hienThiFormSua(1L, model, session);

        // Then
        assertEquals("dongvat/form", result);
        verify(model).addAttribute("dongVat", dongVatTest);
        verify(model).addAttribute(eq("danhSachChuong"), anyList());
    }

    @Test
    void testHienThiFormSua_Admin_KhongTimThay() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.timTheoId(999L)).thenReturn(null);

        // When
        String result = dongVatController.hienThiFormSua(999L, model, session);

        // Then
        assertEquals("redirect:/dongvat?notfound", result);
    }

    @Test
    void testXuLyLuu_Admin_ThanhCong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        doNothing().when(dongVatService).luuHoacCapNhat(dongVatTest);

        // When
        String result = dongVatController.xuLyLuu(dongVatTest, model, session);

        // Then
        assertEquals("redirect:/dongvat?success", result);
        verify(dongVatService).luuHoacCapNhat(dongVatTest);
    }

    @Test
    void testXuLyLuu_Admin_ChuongDay() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        doThrow(new RuntimeException("Chuồng đã đầy")).when(dongVatService).luuHoacCapNhat(dongVatTest);
        // Sửa: Sử dụng mutable list thay vì Collections.emptyList()
        when(chuongService.layChuongCoChoTrong()).thenReturn(new ArrayList<>());
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);

        // When
        String result = dongVatController.xuLyLuu(dongVatTest, model, session);

        // Then
        assertEquals("dongvat/form", result);
        verify(model).addAttribute("error", "Chuồng đã đầy");
    }

    @Test
    void testXuLyLuu_Staff_KhongCoQuyen() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);

        // When
        String result = dongVatController.xuLyLuu(dongVatTest, model, session);

        // Then
        assertEquals("redirect:/error/505", result);
    }

    @Test
    void testXoaDongVat_Admin_ThanhCong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        doNothing().when(dongVatService).xoaTheoId(1L);

        // When
        String result = dongVatController.xoaDongVat(1L, model, session);

        // Then
        assertEquals("redirect:/dongvat?deleted", result);
        verify(dongVatService).xoaTheoId(1L);
    }

    @Test
    void testXoaDongVat_Staff_KhongCoQuyen() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);

        // When
        String result = dongVatController.xoaDongVat(1L, model, session);

        // Then
        assertEquals("redirect:/error/505", result);
    }
}
