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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DongVatControllerTest {

    @Mock private DongVatService dongVatService;
    @Mock private ChuongService chuongService;
    @Mock private HttpSession session;
    @Mock private Model model;
    @InjectMocks private DongVatController dongVatController;

    private User adminUser;
    private DongVat dongVatTest;
    private Chuong chuongTest;

    @BeforeEach
    void setUp() {
        adminUser = new User();
        adminUser.setRole("admin");
        
        dongVatTest = new DongVat("Sư tử", 5.0, "Thú săn mồi", "Khỏe mạnh", "C001");
        dongVatTest.setId(1L);
        
        chuongTest = new Chuong("C001", "Khu A", 10, 5);
    }

    // GET /dongvat với admin → view "dongvat/list", có model "danhSach"
    @Test
    void hienThiDanhSach_admin_success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.layTatCa()).thenReturn(Arrays.asList(dongVatTest));

        String result = dongVatController.hienThiDanhSach(model, session, null);

        assertEquals("dongvat/list", result);
        verify(model).addAttribute("danhSach", Arrays.asList(dongVatTest));
        verify(model).addAttribute("role", "admin");
    }

    // GET /dongvat/them → view "dongvat/form", model có "dongVat", danh sách chuồng
    @Test
    void hienThiFormThem_admin() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.layChuongCoChoTrong()).thenReturn(Arrays.asList(chuongTest));

        String result = dongVatController.hienThiFormThem(model, session);

        assertEquals("dongvat/form", result);
        verify(model).addAttribute(eq("dongVat"), any(DongVat.class));
        verify(model).addAttribute("danhSachChuong", Arrays.asList(chuongTest));
    }

    // GET /dongvat/sua/{id} → form hiển thị thông tin động vật
    @Test
    void hienThiFormSua_admin_found() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.timTheoId(1L)).thenReturn(dongVatTest);
        when(chuongService.layChuongCoChoTrong()).thenReturn(Arrays.asList(chuongTest));
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);

        String result = dongVatController.hienThiFormSua(1L, model, session);

        assertEquals("dongvat/form", result);
        verify(model).addAttribute("dongVat", dongVatTest);
    }

    // GET /dongvat/sua/{id} không tìm thấy → redirect /dongvat?notfound
    @Test
    void hienThiFormSua_admin_notfound() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.timTheoId(999L)).thenReturn(null);

        String result = dongVatController.hienThiFormSua(999L, model, session);

        assertEquals("redirect:/dongvat?notfound", result);
    }

    // POST /dongvat/luu với dữ liệu hợp lệ → redirect /dongvat?success
    @Test
    void xuLyLuu_admin_success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        doNothing().when(dongVatService).luuHoacCapNhat(dongVatTest);

        String result = dongVatController.xuLyLuu(dongVatTest, model, session);

        assertEquals("redirect:/dongvat?success", result);
        verify(dongVatService).luuHoacCapNhat(dongVatTest);
    }

    // GET /dongvat/xoa/{id} → redirect /dongvat?deleted
    @Test
    void xoaDongVat_admin_success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        doNothing().when(dongVatService).xoaTheoId(1L);

        String result = dongVatController.xoaDongVat(1L, model, session);

        assertEquals("redirect:/dongvat?deleted", result);
        verify(dongVatService).xoaTheoId(1L);
    }

    // Không có session/quyền → redirect /error/505
    @Test
    void hienThiDanhSach_notAdmin_redirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(null);

        String result = dongVatController.hienThiDanhSach(model, session, null);

        assertEquals("redirect:/error/505", result);
    }
}
