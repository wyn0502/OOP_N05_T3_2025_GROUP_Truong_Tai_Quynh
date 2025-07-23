// package src;

public class TH6_Student implements Comparable<TH6_Student> {
    String name;
    float gpa;

    // Constructor
    public TH6_Student(String name, float gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public int compareTo(TH6_Student other) {
        if (this.gpa < other.gpa) return 1;
        else if (this.gpa > other.gpa) return -1;
        else return 0;
    }
}
