public class Time {
    private long startTime;
    private long endTime;

    public void batDau() { startTime = System.currentTimeMillis(); }

    public void ketThuc() { endTime = System.currentTimeMillis(); }

    public long layThoiGian() { return endTime - startTime; }

    public void hienThiThoiGian() {
        System.out.println("Thời gian đã trôi qua: " + layThoiGian() + " ms");
    }
}
