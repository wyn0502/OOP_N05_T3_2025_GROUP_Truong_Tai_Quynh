public class Pass {
    static void f(MyNumber m) {
        m.i = 15;
        System.out.println("Trong hàm f, i = " + m.i);
    }

    public static void main(String[] args) {
        MyNumber n = new MyNumber();
        n.i = 14;
        Pass.f(n);
        System.out.println("Sau khi gọi f, i = " + n.i);
    }
}
