puclic class Pass {
    static void f(Number m){
        m.i = 15;
    }
    public static void main (String[] arge){
        Number n = new Number();
        n.i = 14;
        Pass.f(n);
        System.out.println(n.i);
    }
}
