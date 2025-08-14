package com.example.zoo.test;

import com.example.zoo.controller.ChuongController;
import com.example.zoo.model.Chuong;
import com.example.zoo.model.User;
import com.example.zoo.service.ChuongService;
import com.example.zoo.service.DongVatService;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    private User adminUser;
    private User staffUser;
    private User normalUser;

    @BeforeEach
    void setUp() {
        adminUser = new User();
        adminUser.setRole("admin");

        staffUser = new User();
        staffUser.setRole("staff");

        normalUser = new User();
        normalUser.setRole("user");
    }

    @Test
    void testList_AsAdmin_ShouldReturnListView() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.hienThi()).thenReturn(Arrays.asList(new Chuong("C01", "Khu A", 10, 5)));

        String viewName = chuongController.list(model, session, null);

        assertEquals("chuong/list", viewName);
        verify(model).addAttribute(eq("danhSach"), any());
    }

    @Test
    void testList_AsStaff_ShouldReturnListView() {
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);
        when(chuongService.hienThi()).thenReturn(Collections.emptyList());

        String viewName = chuongController.list(model, session, null);
        assertEquals("chuong/list", viewName);
    }

    @Test
    void testList_AsUser_ShouldRedirectError() {
        when(session.getAttribute("loggedInUser")).thenReturn(normalUser);

        String viewName = chuongController.list(model, session, null);
        assertEquals("redirect:/error/505", viewName);
    }

    @Test
    void testThemForm_AsAdmin() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        String viewName = chuongController.themForm(model, session);

        assertEquals("chuong/form", viewName);
        verify(model).addAttribute(eq("mode"), eq("add"));
    }

    @Test
    void testSuaForm_NotFound_ShouldRedirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C01")).thenReturn(null);

        String viewName = chuongController.suaForm("C01", model, session);
        assertEquals("redirect:/chuong", viewName);
    }

    @Test
    void testLuu_AddMode_NewChuong_ShouldRedirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C01")).thenReturn(null);

        Chuong chuong = new Chuong("C01", "Khu A", 10, 5);
        String viewName = chuongController.luu(chuong, "add", session, model);

        assertEquals("redirect:/chuong", viewName);
        verify(chuongService).them(any(Chuong.class));
    }

    @Test
    void testXoa_AsAdmin_NoAnimal_ShouldRedirectDeleted() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.demDongVatTheoMaChuong("C01")).thenReturn(0L);

        String viewName = chuongController.xoa("C01", session, model);
        assertEquals("redirect:/chuong?deleted", viewName);
        verify(chuongService).xoa("C01");
    }
}
