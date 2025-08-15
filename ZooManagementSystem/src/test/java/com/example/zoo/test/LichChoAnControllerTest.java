package com.example.zoo.test;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.model.DongVat;
import com.example.zoo.model.User;
import com.example.zoo.service.LichChoAnService;
import com.example.zoo.service.DongVatService;
import com.example.zoo.repository.DongVatRepository;
import com.example.zoo.controller.LichChoAnController;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LichChoAnControllerTest {

    @Mock
    private LichChoAnService lichChoAnService;
    @Mock
    private DongVatService dongVatService;
    @Mock
    private DongVatRepository dongVatRepository;
    @Mock
    private Model model;
    @Mock
    private HttpSession session;
    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private LichChoAnController lichChoAnController;

    private User adminUser, staffUser;
    private LichChoAn sampleLich;
    private DongVat sampleDongVat;

    @BeforeEach
    void setUp() {
        adminUser = new User();
        adminUser.setRole("admin");
        adminUser.setId(1L);
        
        staffUser = new User();
        staffUser.setRole("staff");
        staffUser.setId(2L);
        
        sampleDongVat = new DongVat();
        
        sampleDongVat.setTen("Hổ Bengal");
        
        sampleLich = new LichChoAn();
        sampleLich.setMaLich("L001");
        sampleLich.setDongVat("Hổ Bengal");
        sampleLich.setThucAn("Thịt tươi");
        sampleLich.setNhanVien("Nguyễn Văn A");
        sampleLich.setThoiGian(LocalDateTime.now().plusHours(1));
    }

    @Test
    void testDanhSach_Admin_Success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(lichChoAnService.getAll()).thenReturn(Arrays.asList(sampleLich));

        String result = lichChoAnController.danhSach(model, session);

        assertEquals("lichchoan/list", result);
        verify(model).addAttribute("danhSachLich", Arrays.asList(sampleLich));
    }

    @Test
    void testDanhSach_Staff_Success() {
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);
        when(lichChoAnService.getAll()).thenReturn(Arrays.asList(sampleLich));

        String result = lichChoAnController.danhSach(model, session);

        assertEquals("lichchoan/list", result);
        verify(model).addAttribute("role", "staff");
    }

    @Test
    void testDanhSach_Unauthorized_Redirect() {
        User regularUser = new User();
        regularUser.setRole("user");
        when(session.getAttribute("loggedInUser")).thenReturn(regularUser);

        String result = lichChoAnController.danhSach(model, session);

        assertEquals("redirect:/error/505", result);
    }

    @Test
    void testHienThiFormThem_Admin_Success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.layTatCa()).thenReturn(Arrays.asList(sampleDongVat));
        when(model.containsAttribute("lich")).thenReturn(false);

        String result = lichChoAnController.hienThiFormThem(model, session);

        assertEquals("lichchoan/add", result);
        verify(model).addAttribute(eq("lich"), any(LichChoAn.class));
    }

    @Test
    void testXuLyThem_ValidData_Success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(lichChoAnService.timTheoId("L001")).thenReturn(null);
        when(dongVatRepository.findByTen("Hổ Bengal")).thenReturn(sampleDongVat);
        when(bindingResult.hasErrors()).thenReturn(false);
        
        sampleLich.setMaLich("L001");

        String result = lichChoAnController.xuLyThem(sampleLich, bindingResult, null, model, session);

        assertEquals("redirect:/lichchoan", result);
        verify(lichChoAnService).themLich(sampleLich);
    }

    @Test
    void testXuLyThem_Unauthorized_Redirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);

        String result = lichChoAnController.xuLyThem(sampleLich, bindingResult, null, model, session);

        assertEquals("redirect:/error/505", result);
        verify(lichChoAnService, never()).themLich(any());
    }

    @Test
    void testHienThiFormSua_Found_ShowForm() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(lichChoAnService.timTheoId("L001")).thenReturn(sampleLich);
        when(dongVatService.layTatCa()).thenReturn(Arrays.asList(sampleDongVat));

        String result = lichChoAnController.hienThiFormSua("L001", model, session);

        assertEquals("lichchoan/edit", result);
        verify(model).addAttribute("lich", sampleLich);
    }

    @Test
    void testHienThiFormSua_NotFound_Redirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(lichChoAnService.timTheoId("L999")).thenReturn(null);

        String result = lichChoAnController.hienThiFormSua("L999", model, session);

        assertEquals("redirect:/lichchoan", result);
    }

    @Test
    void testXoa_Admin_Success() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);

        String result = lichChoAnController.xoa("L001", session);

        assertEquals("redirect:/lichchoan", result);
        verify(lichChoAnService).xoaLich("L001");
    }

    @Test
    void testXoa_Unauthorized_Redirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);

        String result = lichChoAnController.xoa("L001", session);

        assertEquals("redirect:/error/505", result);
        verify(lichChoAnService, never()).xoaLich(any());
    }
}
