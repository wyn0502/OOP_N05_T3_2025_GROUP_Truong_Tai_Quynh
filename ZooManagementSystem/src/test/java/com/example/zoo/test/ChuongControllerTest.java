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
import org.springframework.ui.Model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChuongControllerTest {

    @Mock private ChuongService chuongService;
    @Mock private DongVatService dongVatService;
    @Mock private HttpSession session;
    @Mock private Model model;
    @InjectMocks private ChuongController chuongController;

    private User adminUser, staffUser, userUser;
    private Chuong chuongTest;

    @BeforeEach
    void setUp() {
        adminUser = createUser("admin");
        staffUser = createUser("staff");
        userUser = createUser("user");
        chuongTest = new Chuong("C001", "Khu A", 10, 5);
    }

    private User createUser(String role) {
        User user = new User();
        user.setRole(role);
        return user;
    }

    // GET /chuong với admin → view "chuong/list"
    @Test
    void testList_AsAdmin_ShouldReturnListView() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.hienThi()).thenReturn(Arrays.asList(chuongTest));

        String result = chuongController.list(model, session, null);

        assertEquals("chuong/list", result);
        verify(model).addAttribute("danhSach", Arrays.asList(chuongTest));
        verify(model).addAttribute("role", "admin");
    }

    // GET /chuong với staff → cho phép xem
    @Test
    void testList_AsStaff_ShouldReturnListView() {
        when(session.getAttribute("loggedInUser")).thenReturn(staffUser);
        when(chuongService.hienThi()).thenReturn(Arrays.asList(chuongTest));

        String result = chuongController.list(model, session, null);

        assertEquals("chuong/list", result);
        verify(model).addAttribute("danhSach", Arrays.asList(chuongTest));
        verify(model).addAttribute("role", "staff");
    }

    // User thường → redirect /error/505
    @Test
    void testList_AsUser_ShouldRedirectError() {
        when(session.getAttribute("loggedInUser")).thenReturn(userUser);

        String result = chuongController.list(model, session, null);

        assertEquals("redirect:/error/505", result);
    }

    // View "chuong/form", mode="add"
    @Test
    void testThemForm_AsAdmin() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);

        String result = chuongController.themForm(model, session);

        assertEquals("chuong/form", result);
        verify(model).addAttribute(eq("chuong"), any(Chuong.class));
        verify(model).addAttribute("mode", "add");
    }

    // Không tìm thấy → redirect /chuong
    @Test
    void testSuaForm_NotFound_ShouldRedirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C999")).thenReturn(null);

        String result = chuongController.suaForm("C999", model, session);

        assertEquals("redirect:/chuong", result);
    }

    // Lưu mới thành công → redirect /chuong
    @Test
    void testLuu_AddMode_NewChuong_ShouldRedirect() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(chuongService.timTheoMa("C001")).thenReturn(null);
        doNothing().when(chuongService).them(any(Chuong.class));

        String result = chuongController.luu(chuongTest, "add", session, model);

        assertEquals("redirect:/chuong", result);
        verify(chuongService).them(chuongTest);
    }

    // Không có động vật → redirect /chuong?deleted
    @Test
    void testXoa_AsAdmin_NoAnimal_ShouldRedirectDeleted() {
        when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
        when(dongVatService.demDongVatTheoMaChuong("C001")).thenReturn(0L);
        doNothing().when(chuongService).xoa("C001");

        String result = chuongController.xoa("C001", session, model);

        assertEquals("redirect:/chuong?deleted", result);
        verify(chuongService).xoa("C001");
    }
}
