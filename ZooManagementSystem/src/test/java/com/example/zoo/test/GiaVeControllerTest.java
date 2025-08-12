package com.example.zoo.test;

import com.example.zoo.controller.GiaVeController;
import com.example.zoo.model.GiaVe;
import com.example.zoo.model.User;
import com.example.zoo.service.GiaVeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GiaVeController.class)
class GiaVeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GiaVeService giaVeService;

    private MockHttpSession adminSession;
    private MockHttpSession staffSession;

    @BeforeEach
    void setUp() {
        // Setup admin session
        adminSession = new MockHttpSession();
        User admin = new User();
        admin.setRole("admin");
        adminSession.setAttribute("loggedInUser", admin);

        // Setup staff session
        staffSession = new MockHttpSession();
        User staff = new User();
        staff.setRole("staff");
        staffSession.setAttribute("loggedInUser", staff);
    }

    @Test
    void testHienThiDanhSach_Admin_Success() throws Exception {
        // Arrange - Sử dụng constructor đúng: (String, Double, String, Double)
        GiaVe gv = new GiaVe("Người lớn", 100000.0, "Vé thường", 0.0);
        when(giaVeService.layTatCa()).thenReturn(Arrays.asList(gv));

        // Act & Assert
        mockMvc.perform(get("/giave").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("danhSachVe"))
                .andExpect(view().name("giave/list"));
    }

    @Test
    void testHienThiDanhSach_Staff_Success() throws Exception {
        // Arrange - Staff được xem danh sách
        GiaVe gv = new GiaVe("Trẻ em", 50000.0, "Vé giảm giá", 50.0);
        when(giaVeService.layTatCa()).thenReturn(Arrays.asList(gv));

        // Act & Assert
        mockMvc.perform(get("/giave").session(staffSession))
                .andExpect(status().isOk())
                .andExpected(model().attributeExists("danhSachVe"))
                .andExpected(view().name("giave/list"));
    }

    @Test
    void testHienThiFormThem_Admin_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/giave/add").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ve"))
                .andExpect(view().name("giave/add"));
    }

    @Test
    void testHienThiFormThem_Staff_Redirect() throws Exception {
        // Act & Assert - Staff không được thêm
        mockMvc.perform(get("/giave/add").session(staffSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error/505"));
    }

    @Test
    void testLuuGiaVe_ValidData_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/giave/add")
                        .session(adminSession)
                        .param("loaiVe", "Người lớn")
                        .param("giaCoBan", "100000")
                        .param("lyDoGiamGia", "Vé thường")
                        .param("phanTramGiamGia", "0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/giave?success"));
    }

    @Test
    void testSuaGiaVe_Found_ShowForm() throws Exception {
        // Arrange
        GiaVe giaVe = new GiaVe("VIP", 200000.0, "Vé cao cấp", 10.0);
        when(giaVeService.timTheoId(1L)).thenReturn(giaVe);

        // Act & Assert
        mockMvc.perform(get("/giave/edit/1").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ve", giaVe))
                .andExpect(view().name("giave/edit"));
    }

    @Test
    void testSuaGiaVe_NotFound_Redirect() throws Exception {
        // Arrange
        when(giaVeService.timTheoId(99L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/giave/edit/99").session(adminSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/giave?notfound"));
    }

    @Test
    void testXoaGiaVe_Admin_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/giave/delete/1").session(adminSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/giave?deleted"));
        
        verify(giaVeService, times(1)).xoaTheoId(1L); // Sửa method name
    }

    @Test
    void testXoaGiaVe_Staff_Redirect() throws Exception {
        // Act & Assert - Staff không được xóa
        mockMvc.perform(get("/giave/delete/1").session(staffSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error/505"));
    }
}
