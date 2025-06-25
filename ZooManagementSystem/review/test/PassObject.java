public class PassObject {
    static void f(MyNumber m) {
        m.i = 15;
        System.out.println("Trong hàm f, i = " + m.i);
    }

    public static void main(MyNumber m) {
        MyNumber n = new MyNumber();
        n.i = 14;
        PassObject.f(n);
        System.out.println("Sau khi gọi f, i = " + n.i);
    }
}
