public class TruongTestTime {
    public static void test(String[] args) throws InterruptedException {
        Time t = new Time(10, 30, 45);
        t.hienThiGioPhutGiay();

        t.batDau();
        Thread.sleep(2000);
        t.ketThuc();
        t.hienThiThoiGian();
    }
}
