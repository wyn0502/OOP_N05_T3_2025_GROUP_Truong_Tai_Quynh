import java.util.ArrayList;
import java.util.List;

public class TestList {
    public static void main(String[] args) {
        List<String> danhSach = new ArrayList<>();

        danhSach.add("Táo");
        danhSach.add("Cam");
        danhSach.add("Xoài");
        danhSach.add("Ổi");

        System.out.println("Danh sách: ");
        for (String item : danhSach) {
            System.out.println("- " + item);
        }

    }
}
