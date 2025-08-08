// package com.example.servingwebcontent.test;


// import com.example.zoo.controller.DongVatController;
// import com.example.zoo.model.DongVat;
// import com.example.zoo.service.DongVatService;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;

// import java.util.List;

// import static org.mockito.BDDMockito.given;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(controllers = DongVatController.class)
// class DongVatControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private DongVatService dongVatService;

//     @Test
//     void testHienThiDanhSach() throws Exception {
//         DongVat dv = new DongVat();
//         dv.setId(1L);
//         dv.setTen("Mèo");
//         dv.setTuoi(2);
//         dv.setLoai("Con");
//         dv.setSucKhoe("Tốt");
//         dv.setKhuVuc("A1");

//         given(dongVatService.layTatCa()).willReturn(List.of(dv));

//         mockMvc.perform(get("/dongvat"))
//                 .andExpect(status().isOk())
//                 .andExpect(model().attributeExists("danhSach"))
//                 .andExpect(view().name("dongvat/list"));
//     }

//     @Test
//     void testFormThemMoi() throws Exception {
//         mockMvc.perform(get("/dongvat/them"))
//                 .andExpect(status().isOk())
//                 .andExpect(model().attributeExists("dongVat"))
//                 .andExpect(view().name("dongvat/form"));
//     }
// }

// }
