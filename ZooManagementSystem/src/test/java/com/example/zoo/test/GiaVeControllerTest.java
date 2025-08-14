package com.example.zoo.test;

import com.example.zoo.model.GiaVe;
import com.example.zoo.model.User;
import com.example.zoo.service.GiaVeService;
import com.example.zoo.controller.GiaVeController;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiaVeControllerTest {

    @Mock
    private GiaVeService giaVeService;
    @Mock
    private Model model;
    @Mock
    private HttpSession session;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private GiaVeController giaVeController;

    private User adminUser, regularUser;
    private GiaVe sampleVe;

    @BeforeEach
    void setUp() {
        adminUser = new User();
        adminUser.setRole("admin");
        regularUser = new User();
        regularUser.setRole("user");
        
        sampleVe = new GiaVe();
        sampleVe.setId(1L);
        sampleVe.setLoaiVe("Vé người lớn");
        sampleVe.setGiaCoBan(100000.0);
    }

    @Test
    void testHienThiDanhSach_Admin_Success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(giaVeService.layTatCa()).thenReturn(Arrays.asList(sampleVe));

        String result = giaVeController.danhSachVe(null, null, model, session);

        assertEquals("giave/list", result);
        verify(model).addAttribute("danhSachVe", Arrays.asList(sampleVe));
    }

    @Test
    void testHienThiDanhSach_User_Redirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(regularUser);

        String result = giaVeController.danhSachVe(null, null, model, session);

        assertEquals("redirect:/error/505", result);
    }

    @Test
    void testHienThiFormThem_Admin_Success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);

        String result = giaVeController.hienThiFormThem(model, session);

        assertEquals("giave/add", result);
        verify(model).addAttribute(eq("ve"), any(GiaVe.class));
    }

    @Test
    void testLuuGiaVe_ValidData_Success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = giaVeController.xuLyThemVe(sampleVe, bindingResult, model, session);

        assertEquals("redirect:/giave?success", result);
        verify(giaVeService).luu(sampleVe);
    }

    @Test
    void testLuuGiaVe_InvalidData_ReturnForm() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = giaVeController.xuLyThemVe(sampleVe, bindingResult, model, session);

        assertEquals("giave/add", result);
        verify(giaVeService, never()).luu(any());
    }

    @Test
    void testSuaGiaVe_Found_ShowForm() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(giaVeService.timTheoId(1L)).thenReturn(sampleVe);

        String result = giaVeController.hienThiFormSua(1L, model, session);

        assertEquals("giave/edit", result);
        verify(model).addAttribute("ve", sampleVe);
    }

    @Test
    void testSuaGiaVe_NotFound_Redirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(giaVeService.timTheoId(99L)).thenReturn(null);

        String result = giaVeController.hienThiFormSua(99L, model, session);

        assertEquals("redirect:/giave?notfound", result);
    }

    @Test
    void testXoaGiaVe_Admin_Success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);

        String result = giaVeController.xoaVe(1L, model, session);

        assertEquals("redirect:/giave?deleted", result);
        verify(giaVeService).xoaTheoId(1L);
    }

    @Test
    void testSearchByKeyId_NotFound() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(giaVeService.timTheoId(999L)).thenReturn(null);

        String result = giaVeController.danhSachVe("999", null, model, session);

        assertEquals("giave/list", result);
        verify(model).addAttribute("searchResult", "Không tìm thấy vé với ID: 999");
    }

    @Test
    void testSearchByKeyword() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(giaVeService.layTatCa()).thenReturn(Arrays.asList(sampleVe));

        String result = giaVeController.danhSachVe(null, "người lớn", model, session);

        assertEquals("giave/list", result);
        verify(model).addAttribute("keyword", "người lớn");
    }
}
