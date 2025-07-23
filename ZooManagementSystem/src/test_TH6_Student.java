// package src;

import java.util.Arrays;

public class test_TH6_Student {
    public static void runTest() {
        TH6_Student TH6_Student1 = new TH6_Student("Alice", 3.7f);
        TH6_Student TH6_Student2 = new TH6_Student("Bob", 3.5f);
        TH6_Student TH6_Student3 = new TH6_Student("Charlie", 3.9f);
        TH6_Student TH6_Student4 = new TH6_Student("David", 3.7f);

        TH6_Student[] TH6_Students = { TH6_Student1, TH6_Student2, TH6_Student3, TH6_Student4 };

        System.out.println("Before sorting:");
        for (TH6_Student TH6_Student : TH6_Students) {
            System.out.println(TH6_Student.name + ": " + TH6_Student.gpa);
        }

        Arrays.sort(TH6_Students);

        System.out.println("\nAfter sorting:");
        for (TH6_Student TH6_Student : TH6_Students) {
            System.out.println(TH6_Student.name + ": " + TH6_Student.gpa);
        }
    }
}
