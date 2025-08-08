// package com.example.zoo.test;

// import com.example.zoo.service.ChuongService;
// import com.example.zoo.manager.ChuongManager;
// import com.example.zoo.model.Chuong;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.*;

// class ChuongServiceTest {

//     private ChuongService service;

//     @BeforeEach
//     void setUp() {
//         ChuongManager manager = new ChuongManager();
//         service = new ChuongService(manager);
//     }

//     @Test
//     void testThemChuong() {
//         Chuong c = new Chuong("C01", "Khu A", 10, 3);
//         service.them(c);
//         assertEquals(1, service.hienThi().size());
//         assertEquals("C01", service.hienThi().get(0).getMaChuong());
//     }

//     @Test
//     void testSuaChuong() {
//         Chuong c = new Chuong("C02", "Khu B", 15, 4);
//         service.them(c);
//         Chuong update = new Chuong("C02", "Khu BB", 20, 5);
//         service.sua("C02", update);
//         Chuong result = service.timTheoMa("C02");
//         assertEquals("Khu BB", result.getTenKhuVuc());
//         assertEquals(20, result.getSucChuaToiDa());
//         assertEquals(5, result.getSoLuongHienTai());
//     }

//     @Test
//     void testXoaChuong() {
//         Chuong c = new Chuong("C03", "Khu C", 8, 2);
//         service.them(c);
//         assertEquals(1, service.hienThi().size());
//         service.xoa("C03");
//         assertEquals(0, service.hienThi().size());
//     }
// }
