import test.TestUser;
import truong.TruongTestRecursion;
import truong.TruongTestTime;
import truong.TruongTestUser;
import test.TestTime;
import test.TestRecursion;
public class App {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");

        // TestGiaVe.Inve();
        // PassObject.main(new MyNumber());
        // BreakAndContinue.testFor();
        // BreakAndContinue.testWhile();
        // BreakAndContinue.testDoWhile();
        // LichChoAn.inThoiGian();

        //TestKhi.test();
        //TestSuTu.test();
        TestUser.main(new String[] {});
        
        TruongTestUser.test(new String[] {});
        TruongTestTime.test(new String[] {});
        TruongTestRecursion.test(new String[] {});
    }
}
