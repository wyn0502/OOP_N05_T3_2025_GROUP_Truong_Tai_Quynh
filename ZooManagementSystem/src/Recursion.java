public class Recursion {
    public long tinhGiaiThua(int n) {
        if (n <= 1)
            return 1;
        else
            return n * tinhGiaiThua(n - 1);
    }
    public static void main(String[] args){
        Recursion r = new Recursion();
        System.out.println("Giai thừa 5 là: " + r.tinhGiaiThua(5));;
}
}
