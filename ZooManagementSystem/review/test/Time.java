package review.test;

public class Time {
    int hour;
    int minute;
    int second;

    private long startTime;
    private long endTime;

    public Time() { setTime(0, 0, 0); }

    public Time(int h) { setTime(h, 0, 0); }

    public Time(int h, int m) { setTime(h, m, 0); }

    public Time(int h, int m, int s) { setTime(h, m, s); }

    public void setTime(int h, int m, int s) {
        this.hour = h;
        this.minute = m;
        this.second = s;
    }

    public void batDau() { startTime = System.currentTimeMillis(); }

    public void ketThuc() { endTime = System.currentTimeMillis(); }

    public long layThoiGian() { return endTime - startTime; }

    public void hienThiThoiGian() {
        System.out.println("Thời gian đã trôi qua: " + layThoiGian() + " ms\n");
    }

    public void hienThiGioPhutGiay() {
        System.out.printf("Thời gian: %02d:%02d:%02d\n", hour, minute, second);
    }
}
