package review.test;

public class TruongTestUser {
    public static void test(String[] args) {
        User user = new User("Xuân Trường", 16, "truong@gmail.com");

        System.out.println("Thông tin ban đầu:");
        System.out.println(user);

        user.setHoTen("Trường");
        user.setTuoi(19);
        user.setEmail("truong@gmail.com\n");

        System.out.println("Thông tin sau khi cập nhật:");
        System.out.println(user);
    }
}
