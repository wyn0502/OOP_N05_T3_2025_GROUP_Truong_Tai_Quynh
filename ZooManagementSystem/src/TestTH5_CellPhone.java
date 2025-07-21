public class TestTH5_CellPhone {
    public static void testTune(String[] args) {
        TH5_CellPhone phone = new TH5_CellPhone();

        System.out.println("Test với Tune:");
        Tune simpleTune = new Tune();
        phone.ring(simpleTune);

        System.out.println("\nTest với ObnoxiousTune:");
        ObnoxiousTune annoyingTune = new ObnoxiousTune();
        phone.ring(annoyingTune);
    }
}
