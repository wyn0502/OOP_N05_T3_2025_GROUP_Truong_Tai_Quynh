public class BreakAndContinue {
    public static void testFor (){
        for (int i = 0; i < 100; i++){
        if (i == 74) break;
        if (i % 9 != 0) continue;
        System.out.println(i);}
    }

        public static void testWhile (){
            int a = 1;
            while (a <= 5){
            System.out.println(a);
            a++;
            }
    }
}
