package com.example.zoo.test;

import com.example.zoo.model.Chuong;
import com.example.zoo.service.ChuongService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChuongController.class)
public class ChuongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChuongService service;

    @Test
    void testListChuong() throws Exception {
        Mockito.when(service.hienThi()).thenReturn(Collections.singletonList(
                new Chuong("C04", "Khu D", 12, 6)
        ));
        mockMvc.perform(get("/chuong"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("danhSach"));
    }
}
