public class TruongTestTime {

    public static void test(String[] args) throws InterruptedException {
        Time timer = new Time();

        System.out.println("Bắt đầu đo thời gian...");
        timer.batDau();

        Thread.sleep(4000);

        timer.ketThuc();
        timer.hienThiThoiGian();
    }

    public static void muoiNganNam(String[] args) throws InterruptedException {
        Time time10 = new Time();

        time10.batDau();
        Thread.sleep(2000);
        time10.ketThuc();

        long elapsed = time10.layThoiGian();
        if (elapsed % 2 == 0) {
            System.out.println("10 Ngàn Năm!");
        } else {
            System.out.println("Chưa tới 10 Ngàn Năm");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        test(args);
        muoiNganNam(args);
    }
}
