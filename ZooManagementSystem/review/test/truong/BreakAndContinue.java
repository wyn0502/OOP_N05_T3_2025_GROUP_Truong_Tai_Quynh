public class BreakAndContinue {
    public static void testFor() {
        System.out.printf("Dang test For:");
        for (int i = 0; i < 100; i++) {
            if (i == 74)
                break;
            if (i % 9 != 0)
                continue;
            System.out.printf(" " + i);
        }
    }

    public static void testWhile() {
        int a = 1;
System.out.println(" ");
        System.out.printf("Dang test While:");
        while (a <= 5) {
            System.out.printf(" " + a);
            a++;
        }
    }

    public static void testDoWhile(){
        int a = 0;
System.out.println(" ");
        System.out.printf("Dang test Do While:" );
        do {
            System.out.printf(" " + a);
            a++;
        } while(a <= 2);
    }
}
