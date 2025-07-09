package review.test;

public class TestTime-quynh{
    public static void main(String[] args) {
        Time t1 = new Time(); // 00:00:00
        Time t2 = new Time(14, 30, 15); // 14:30:15

        System.out.println("=== Thời gian t1 ===");
        System.out.println(t1);

        System.out.println("=== Thời gian t2 ===");
        System.out.println(t2);
    }
}
