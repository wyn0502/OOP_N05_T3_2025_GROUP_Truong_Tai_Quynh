package com.example.zoo.test;

import com.example.zoo.controller.DongVatController;
import com.example.zoo.model.Chuong;
import com.example.zoo.model.DongVat;
import com.example.zoo.model.User;
import com.example.zoo.service.ChuongService;
import com.example.zoo.service.DongVatService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DongVatController.class)
class DongVatControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DongVatService dongVatService;

    @MockBean
    ChuongService chuongService;

    MockHttpSession adminSession;

    @BeforeEach
    void setup() {
        adminSession = new MockHttpSession();
        User admin = new User();
        admin.setRole("admin");
        adminSession.setAttribute("loggedInUser", admin);
    }

    @Test
    void hienThiDanhSach_admin_success() throws Exception {
        when(dongVatService.layTatCa()).thenReturn(List.of());
        mockMvc.perform(get("/dongvat").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("dongvat/list"));
    }

    @Test
    void hienThiFormThem_admin() throws Exception {
        when(chuongService.layChuongCoChoTrong())
                .thenReturn(List.of(new Chuong("C01", "Khu A", 5, 2)));
        mockMvc.perform(get("/dongvat/them").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("dongvat/form"))
                .andExpect(model().attributeExists("dongVat"));
    }

    @Test
    void hienThiFormSua_admin_found() throws Exception {
        DongVat dv = new DongVat("Hổ", 7, "Hổ", "Tốt", "C01");
        when(dongVatService.timTheoId(1L)).thenReturn(dv);
        when(chuongService.layChuongCoChoTrong()).thenReturn(List.of());

        mockMvc.perform(get("/dongvat/sua/1").session(adminSession))
                .andExpect(status().isOk())
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
                        .param("maChuong", "C01"))
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
