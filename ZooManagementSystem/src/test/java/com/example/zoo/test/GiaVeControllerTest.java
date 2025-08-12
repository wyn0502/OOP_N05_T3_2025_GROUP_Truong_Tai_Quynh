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
    private MockHttpSession userSession;

    @BeforeEach
    void setUp() {
        // Setup admin session
        adminSession = new MockHttpSession();
        User admin = new User();
        admin.setRole("admin");
        adminSession.setAttribute("loggedInUser", admin);

        // Setup user session
        userSession = new MockHttpSession();
        User user = new User();
        user.setRole("user");
        userSession.setAttribute("loggedInUser", user);
    }

    @Test
    void testHienThiDanhSach_Admin_Success() throws Exception {
        // Arrange
        GiaVe gv = new GiaVe("Người lớn", 100000, "Vé thường");
        when(giaVeService.layTatCa()).thenReturn(Arrays.asList(gv));

        // Act & Assert
        mockMvc.perform(get("/giave").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("danhSach"))
                .andExpect(view().name("giave/list"));
    }

    @Test
    void testHienThiDanhSach_User_Redirect() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/giave").session(userSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error/505"));
    }

    @Test
    void testHienThiFormThem_Admin_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/giave/them").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("giaVe"))
                .andExpect(model().attribute("mode", "add"))
                .andExpect(view().name("giave/form"));
    }

    @Test
    void testLuuGiaVe_ValidData_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/giave/luu")
                        .session(adminSession)
                        .param("loaiVe", "Người lớn")
                        .param("giaGoc", "100000")
                        .param("moTa", "Vé thường"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/giave"));
    }

    @Test
    void testLuuGiaVe_InvalidData_ReturnForm() throws Exception {
        // Act & Assert - POST với dữ liệu không hợp lệ
        mockMvc.perform(post("/giave/luu")
                        .session(adminSession)
                        .param("loaiVe", "") // Trống
                        .param("giaGoc", "-1000") // Âm
                        .param("moTa", ""))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("giave/form"));
    }

    @Test
    void testSuaGiaVe_Found_ShowForm() throws Exception {
        // Arrange
        GiaVe giaVe = new GiaVe("VIP", 200000, "Vé cao cấp");
        when(giaVeService.timTheoId(1L)).thenReturn(giaVe);

        // Act & Assert
        mockMvc.perform(get("/giave/sua/1").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(model().attribute("giaVe", giaVe))
                .andExpect(model().attribute("mode", "edit"))
                .andExpect(view().name("giave/form"));
    }

    @Test
    void testSuaGiaVe_NotFound_Redirect() throws Exception {
        // Arrange
        when(giaVeService.timTheoId(99L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/giave/sua/99").session(adminSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/giave"));
    }

    @Test
    void testXoaGiaVe_Admin_Success() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/giave/xoa/1").session(adminSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/giave"));
        
        verify(giaVeService, times(1)).xoa(1L);
    }
}
