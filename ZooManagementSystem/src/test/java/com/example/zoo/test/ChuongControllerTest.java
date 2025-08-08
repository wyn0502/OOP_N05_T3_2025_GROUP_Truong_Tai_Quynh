// package com.example.zoo.test;

// import com.example.zoo.controller.ChuongController;
// import com.example.zoo.model.Chuong;
// import com.example.zoo.model.User;
// import com.example.zoo.service.ChuongService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.ui.Model;

// import jakarta.servlet.http.HttpSession;

// import java.util.Arrays;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.*;

// class ChuongControllerTest {

//     @Mock
//     private ChuongService chuongService;

//     @Mock
//     private HttpSession session;

//     @Mock
//     private Model model;

//     @InjectMocks
//     private ChuongController chuongController;

//     private User adminUser;
//     private User normalUser;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         adminUser = new User();
//         adminUser.setRole("admin");

//         normalUser = new User();
//         normalUser.setRole("user");
//     }

//     @Test
//     void testList_AsAdmin_ShouldReturnListView() {
//         when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
//         when(chuongService.hienThi()).thenReturn(Arrays.asList(new Chuong("C01", "Khu A", 10, 5)));

//         String viewName = chuongController.list(model, session);

//         assertEquals("chuong/list", viewName);
//         verify(model).addAttribute(eq("danhSach"), any());
//     }

//     @Test
//     void testList_AsUser_ShouldRedirectToError() {
//         when(session.getAttribute("loggedInUser")).thenReturn(normalUser);

//         String viewName = chuongController.list(model, session);

//         assertEquals("redirect:/error/505", viewName);
//     }

//     @Test
//     void testThemForm_AsAdmin_ShouldReturnForm() {
//         when(session.getAttribute("loggedInUser")).thenReturn(adminUser);

//         String viewName = chuongController.themForm(model, session);

//         assertEquals("chuong/form", viewName);
//         verify(model).addAttribute(eq("mode"), eq("add"));
//     }

//     @Test
//     void testSuaForm_NotFound_ShouldRedirect() {
//         when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
//         when(chuongService.timTheoMa("C01")).thenReturn(null);

//         String viewName = chuongController.suaForm("C01", model, session);

//         assertEquals("redirect:/chuong", viewName);
//     }

//     @Test
//     void testLuu_AddMode_NewChuong_ShouldRedirect() {
//         when(session.getAttribute("loggedInUser")).thenReturn(adminUser);
//         when(chuongService.timTheoMa("C01")).thenReturn(null);

//         Chuong chuong = new Chuong("C01", "Khu A", 10, 5);

//         String viewName = chuongController.luu(chuong, "add", session, model);

//         assertEquals("redirect:/chuong", viewName);
//         verify(chuongService).them(chuong);
//     }

//     @Test
//     void testXoa_AsAdmin_ShouldRedirect() {
//         when(session.getAttribute("loggedInUser")).thenReturn(adminUser);

//         String viewName = chuongController.xoa("C01", session, model);

//         assertEquals("redirect:/chuong", viewName);
//         verify(chuongService).xoa("C01");
//     }
// }
