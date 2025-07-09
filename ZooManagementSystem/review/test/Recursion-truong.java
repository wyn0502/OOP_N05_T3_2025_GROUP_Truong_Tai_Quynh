package review.test;

public class Recursion-truong {
    public int tinhGiaiThua(int n) {
        if (n <= 1)
            return 1;
        return n * tinhGiaiThua(n - 1);
    }

    public int fibonacci(int n) {
        if (n <= 1)
            return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public void inDayFibonacci(int soPhanTu) {
        System.out.print("Dãy Fibonacci (" + soPhanTu + " phần tử): ");
        for (int i = 0; i < soPhanTu; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println();
    }

    public String daoNguocChuoi(String str) {
        if (str == null || str.length() <= 1)
            return str;
        return daoNguocChuoi(str.substring(1)) + str.charAt(0);
    }
}
