public class TestNN {
    public static void test() {
        NNCollection list = new NNCollection();
        list.insert(new NameNumber1("Tran", "0123"));
        list.insert(new NameNumber1("Le", "0456"));
        list.insert(new NameNumber1("Nguyen", "0789"));
        list.insert(new NameNumber1("Pham", "0111"));

        System.out.println("Le: " + list.findNumber("Le"));
        System.out.println("Pham: " + list.findNumber("Pham"));
        System.out.println("Dang: " + list.findNumber("Dang"));
    }
}
