package src;

public class Recursion {
    public long tinhGiaiThua(int n) {
        if (n <= 1) return 1;
        return n * tinhGiaiThua(n - 1);
    }
}
