public class App {
    public static void main(String[] args) throws Exception {
        // tem.out.println("Hello, World!");

        // TestGiaVe.Inve();
        // PassObject.main(new MyNumber());
        // BreakAndContinue.testFor();
        // BreakAndContinue.testWhile();
        // BreakAndContinue.testDoWhile();

        DongVat Con_Khi = new DongVat();
        Con_Khi.keu();
        Con_Khi.nhapTuoi(5);
        System.out.printf("Tuoi: %d", Con_Khi.hienThiTuoi());
    }
}
