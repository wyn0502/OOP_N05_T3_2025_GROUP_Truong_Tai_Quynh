// package src;

import test.TestLichChoAnManager;

import src.TestLichChoAn;
import src.TestNhanVienManager;
import test.TestChuongManager;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {
        // List<LichChoAn> ds = new ArrayList<>();
        // Scanner scan = new Scanner(System.in);
        // LichChoAnManager manager = testLichChoAnManager.getManager();
        //
        // System.out.println("Danh sách ban đầu");
        // manager.printLichChoAn();
        //
        // System.out.println("\nTạo thêm lịch mới");
        // LichChoAn newLich = new LichChoAn("004", "Gấu", "Mật ong", "NV04", "11:00");
        // manager.Create(newLich);
        // manager.printLichChoAn();
        //
        // System.out.println("\nXóa lịch có mã '002'");
        // manager.Delete("002");
        // manager.printLichChoAn();
        //
        // System.out.println("\nSửa thời gian có mã '003'");
        // manager.Edit("003");
        // manager.printLichChoAn();
        //
        //
        //
        //
        // testChuongManager test = new testChuongManager();
        // test.chayThu();
        //
        // testDongVatManager test1 = new testDongVatManager();
        // test.chayThu();
        //
        // TestTH5_CellPhone test2 = new TestTH5_CellPhone();
        // test2.testTune();

        // TestGiaVeManager test = new TestGiaVeManager();
        // test.chạyThu();

        // TestLichChoAn.runTest();

        // test_TH6_Student test = new test_TH6_Student();
        // test.runTest();


        TestNhanVienManager test1 = new TestNhanVienManager();
        test1.test(); 
    }
}
