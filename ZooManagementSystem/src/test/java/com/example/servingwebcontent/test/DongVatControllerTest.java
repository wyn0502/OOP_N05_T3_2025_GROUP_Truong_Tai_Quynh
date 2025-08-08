package com.example.servingwebcontent.test;

import com.example.zoo.model.DongVat;
import com.example.zoo.service.DongVatService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DongVatController.class)
public class DongVatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DongVatService dongVatService;

    @Test
    void testHienThiDanhSach() throws Exception {
        DongVat dv = new DongVat("Meo", 2, "Con", "Tốt", "A1");
        given(dongVatService.layTatCa()).willReturn(Arrays.asList(dv));

        mockMvc.perform(get("/dongvat"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("danhSach"))
                .andExpect(view().name("dongvat/list"));
    }

    @Test
    void testFormThemMoi() throws Exception {
        mockMvc.perform(get("/dongvat/them"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dongVat"))
                .andExpect(view().name("dongvat/form"));
    }
}
