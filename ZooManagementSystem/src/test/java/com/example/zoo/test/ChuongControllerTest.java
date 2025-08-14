package com.example.zoo.test;

import com.example.zoo.model.Chuong;
import com.example.zoo.model.DongVat;
import com.example.zoo.model.User;
import com.example.zoo.service.ChuongService;
import com.example.zoo.service.DongVatService;
import com.example.zoo.controller.ChuongController;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ChuongControllerTest {

    @Mock
    private ChuongService chuongService;

    @Mock
    private DongVatService dongVatService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private ChuongController chuongController;

    private MockMvc mockMvc;
    private User adminUser;
    private User staffUser;
    private User guestUser;
    private Chuong chuongTest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(chuongController).build();
        
        adminUser = new User();
        adminUser.setRole("admin");
        
        staffUser = new User();
        staffUser.setRole("staff");
        
        guestUser = new User();
        guestUser.setRole("guest");
        
        chuongTest = new Chuong("C001", "Khu A", 10, 5);
    }

    @Test
    void testList_Admin_ThanhCong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.hienThi()).thenReturn(Arrays.asList(chuongTest));

        // When
        String result = chuongController.list(model, session, null);

        // Then
        assertEquals("chuong/list", result);
        verify(model).addAttribute("danhSach", Arrays.asList(chuongTest));
        verify(model).addAttribute("role", "admin");
    }

    @Test
    void testList_Staff_ThanhCong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);
        when(chuongService.hienThi()).thenReturn(Arrays.asList(chuongTest));

        // When
        String result = chuongController.list(model, session, null);

        // Then
        assertEquals("chuong/list", result);
        verify(model).addAttribute("danhSach", Arrays.asList(chuongTest));
        verify(model).addAttribute("role", "staff");
    }

    @Test
    void testList_Guest_KhongCoQuyen() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(guestUser);

        // When
        String result = chuongController.list(model, session, null);

        // Then
        assertEquals("redirect:/error/505", result);
    }

    @Test
    void testList_TimKiem_TimThay() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);

        // When
        String result = chuongController.list(model, session, "C001");

        // Then
        assertEquals("chuong/list", result);
        verify(model).addAttribute("danhSach", Arrays.asList(chuongTest));
        verify(model).addAttribute("searchResult", "Tìm thấy 1 kết quả cho mã chuồng: C001");
        verify(model).addAttribute("searchValue", "C001");
    }

    @Test
    void testList_TimKiem_KhongTimThay() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C999")).thenReturn(null);

        // When
        String result = chuongController.list(model, session, "C999");

        // Then
        assertEquals("chuong/list", result);
        verify(model).addAttribute("danhSach", Collections.emptyList());
        verify(model).addAttribute("searchResult", "Không tìm thấy chuồng với mã: C999");
    }

    @Test
    void testThemForm_Admin_ThanhCong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);

        // When
        String result = chuongController.themForm(model, session);

        // Then
        assertEquals("chuong/form", result);
        verify(model).addAttribute(eq("chuong"), any(Chuong.class));
        verify(model).addAttribute("mode", "add");
    }

    @Test
    void testThemForm_Staff_KhongCoQuyen() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);

        // When
        String result = chuongController.themForm(model, session);

        // Then
        assertEquals("redirect:/error/505", result);
    }

    @Test
    void testSuaForm_Admin_TimThay() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);

        // When
        String result = chuongController.suaForm("C001", model, session);

        // Then
        assertEquals("chuong/form", result);
        verify(model).addAttribute("chuong", chuongTest);
        verify(model).addAttribute("mode", "edit");
    }

    @Test
    void testSuaForm_Admin_KhongTimThay() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C999")).thenReturn(null);

        // When
        String result = chuongController.suaForm("C999", model, session);

        // Then
        assertEquals("redirect:/chuong", result);
    }

    @Test
    void testLuu_ThemMoi_ThanhCong() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C001")).thenReturn(null);
        doNothing().when(chuongService).them(any(Chuong.class));

        // When
        String result = chuongController.luu(chuongTest, "add", session, model);

        // Then
        assertEquals("redirect:/chuong", result);
        verify(chuongService).them(chuongTest);
        assertEquals(0, chuongTest.getSoLuongHienTai());
    }

    @Test
    void testLuu_ThemMoi_MaDaTonTai() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C001")).thenReturn(chuongTest);

        // When
        String result = chuongController.luu(chuongTest, "add", session, model);

        // Then
        assertEquals("chuong/form", result);
        verify(model).addAttribute("error", "Mã chuồng đã tồn tại!");
        verify(chuongService, never()).them(any());
    }

    @Test
    void testLuu_CapNhat_ThanhCong() {
        // Given
        Chuong chuongCu = new Chuong("C001", "Khu A", 10, 7);
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C001")).thenReturn(chuongCu);
        doNothing().when(chuongService).sua(anyString(), any(Chuong.class));

        // When
        String result = chuongController.luu(chuongTest, "edit", session, model);

        // Then
        assertEquals("redirect:/chuong", result);
        verify(chuongService).sua("C001", chuongTest);
        assertEquals(7, chuongTest.getSoLuongHienTai());
    }

    @Test
    void testXoa_Admin_KhongCoDongVat() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.demDongVatTheoMaChuong("C001")).thenReturn(0L);
        doNothing().when(chuongService).xoa("C001");

        // When
        String result = chuongController.xoa("C001", session, model);

        // Then
        assertEquals("redirect:/chuong?deleted", result);
        verify(chuongService).xoa("C001");
    }

    @Test
    void testXoa_Admin_CoDongVat() {
        // Given
        DongVat dongVat = new DongVat("Sư tử", 5.0, "Thú săn mồi", "Khỏe mạnh", "C001");
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.demDongVatTheoMaChuong("C001")).thenReturn(1L);
        when(dongVatService.layDongVatTheoMaChuong("C001")).thenReturn(Arrays.asList(dongVat));
        when(chuongService.hienThi()).thenReturn(Arrays.asList(chuongTest));

        // When
        String result = chuongController.xoa("C001", session, model);

        // Then
        assertEquals("chuong/list", result);
        verify(model).addAttribute(eq("error"), contains("Không thể xóa chuồng C001 vì còn 1 động vật"));
        verify(chuongService, never()).xoa(anyString());
    }

    @Test
    void testXoa_Staff_KhongCoQuyen() {
        // Given
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);

        // When
        String result = chuongController.xoa("C001", session, model);

        // Then
        assertEquals("redirect:/error/505", result);
    }
}
