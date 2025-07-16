package src;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
// import review.test.TruongTestRecursion;
// import review.test.TruongTestUser;
// import review.test.TruongTestTime;

public class App {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");

        // TestGiaVe.Inve();
        // PassObject.main(new MyNumber());
        // BreakAndContinue.testFor();
        // BreakAndContinue.testWhile();
        // BreakAndContinue.testDoWhile();
        // LichChoAn.inThoiGian();

        // TestKhi.test();
        // TestSuTu.test();

        // TruongTestUser.test(new String[] {});
        // TruongTestTime.test(new String[] {});
        // TruongTestRecursion.test();

//        TestNN.test();

//        NNCollection collection = new NNCollection();
//        collection.insert(new NameNumber("Tran", "123"));
//        collection.insert(new NameNumber("Le", "456"));
//        collection.insert(new NameNumber("Nguyen", "789"));
//
//        collection.printAll();
//
//        System.out.println("Tìm số của Le: " + collection.findNumber("Le"));
//        System.out.println("Tìm số của Vo: " + collection.findNumber("Vo"));


//         Book book1 = new Book("Doraemon", "Fujiko F. Fujio");
//         Book book2 = new Book("Java Programming", "James Gosling");

//         //ArrayList<Book> myBooks = new ArrayList<>();
//         List<Book> myBooks = new ArrayList<>();
//         myBooks.add(book1);
//         myBooks.add(book2);

//         Library library = new Library(myBooks);

//         for (Book b : library.getList()) {
//             System.out.println(b);
//         }
        List<LichChoAn> ds = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        LichChoAnManager manager = testLichChoAnManager.getManager();

        System.out.println("Danh sách ban đầu");
        manager.printLichChoAn();

        System.out.println("\nTạo thêm lịch mới");
        LichChoAn newLich = new LichChoAn("004", "Gấu", "Mật ong", "NV04", "11:00");
        manager.Create(newLich);
        manager.printLichChoAn();

        System.out.println("\nXóa lịch có mã 'L02'");
        manager.Delete("L02");
        manager.printLichChoAn();

        System.out.println("\nSửa thời gian có mã 'L03'");
        manager.Edit("003");
        manager.printLichChoAn();

//        Book book1 = new Book("Doraemon", "Fujiko F. Fujio");
//        Book book2 = new Book("Java Programming", "James Gosling");
//
//        //ArrayList<Book> myBooks = new ArrayList<>();
//        List<Book> myBooks = new ArrayList<>();
//        myBooks.add(book1);
//        myBooks.add(book2);
//
//        Library library = new Library(myBooks);
//
//        for (Book b : library.getList()) {
//            System.out.println(b);

        testChuongManager test = new testChuongManager();
        test.chayThu();

        testDongVatManager test1 = new testDongVatManager();
        test.chayThu();

    }
}
    
    

