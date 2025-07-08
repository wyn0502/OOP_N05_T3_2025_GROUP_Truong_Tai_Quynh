package review.test;

public class TruongTestRecursion {
    public static void test(String[] args) {
        Recursion recursion = new Recursion();

        int n = 3;
        System.out.println("Giai thừa của " + n +
                           " là: " + recursion.tinhGiaiThua(n));

        int soPhanTu = 6;
        recursion.inDayFibonacci(soPhanTu);

        String chuoiGoc = "truong";
        String chuoiDao = recursion.daoNguocChuoi(chuoiGoc);
        System.out.println("Chuỗi gốc: " + chuoiGoc);
        System.out.println("Chuỗi đảo ngược: " + chuoiDao);
    }
}
