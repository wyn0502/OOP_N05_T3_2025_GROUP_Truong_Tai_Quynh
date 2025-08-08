package com.example.zoo.test;

import com.example.zoo.model.DongVat;
import com.example.zoo.model.User;
import com.example.zoo.service.DongVatService;
import com.example.zoo.controller.DongVatController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DongVatController.class)
class DongVatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DongVatService dongVatService;

    private MockHttpSession adminSession;

    @BeforeEach
    void setup() {
        adminSession = new MockHttpSession();
        User admin = new User();
        admin.setRole("admin");
        adminSession.setAttribute("loggedInUser", admin);
    }

    @Test
    void hienThiDanhSach_admin_success() throws Exception {
        DongVat dv = new DongVat("Khỉ", 4, "Linh trưởng", "Tốt", "Khu A");
        when(dongVatService.layTatCa()).thenReturn(Arrays.asList(dv));

        mockMvc.perform(get("/dongvat").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("danhSach"))
                .andExpect(view().name("dongvat/list"));
    }

    @Test
    void hienThiFormThem_admin_returnsForm() throws Exception {
        mockMvc.perform(get("/dongvat/them").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dongVat"))
                .andExpect(view().name("dongvat/form"));
    }

    @Test
    void hienThiFormSua_admin_found() throws Exception {
        DongVat dv = new DongVat("Hổ", 7, "Hổ", "Tốt", "Khu B");
        when(dongVatService.timTheoId(1L)).thenReturn(dv);

        mockMvc.perform(get("/dongvat/sua/1").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dongVat"))
                .andExpect(view().name("dongvat/form"));
    }

    @Test
    void hienThiFormSua_admin_notfound() throws Exception {
        when(dongVatService.timTheoId(99L)).thenReturn(null);
        mockMvc.perform(get("/dongvat/sua/99").session(adminSession))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void xuLyLuu_admin_success() throws Exception {
        mockMvc.perform(post("/dongvat/luu")
                        .session(adminSession)
                        .param("ten", "Gấu")
                        .param("tuoi", "3")
                        .param("loai", "Gấu")
                        .param("sucKhoe", "Tốt")
                        .param("khuVuc", "Khu C"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void xoaDongVat_admin_success() throws Exception {
        mockMvc.perform(get("/dongvat/xoa/1").session(adminSession))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void hienThiDanhSach_notAdmin_redirect() throws Exception {
        mockMvc.perform(get("/dongvat"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error/505"));
    }
}
