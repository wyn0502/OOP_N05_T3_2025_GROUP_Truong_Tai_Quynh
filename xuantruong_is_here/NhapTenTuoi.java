import java.util.Scanner;

public class NhapTenTuoi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ten: ");
        String name = scanner.nextLine();

        System.out.print("Nhap tuoi: ");
        int age = scanner.nextInt();

        System.out.println("Xin chao, " + name + "!");
        System.out.println("Ban " + age + " tuoi.");

        scanner.close();
    }
}
