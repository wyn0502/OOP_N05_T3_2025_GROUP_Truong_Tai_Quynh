package review.test;

public class User-truong{
    private String hoTen;
    private int tuoi;
    private String email;

    public User(String hoTen, int tuoi, String email) {
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.email = email;
    }

    public String getHoTen() { return hoTen; }

    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public int getTuoi() { return tuoi; }

    public void setTuoi(int tuoi) { this.tuoi = tuoi; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String toString() {
        return "Họ tên: " + hoTen + ", Tuổi: " + tuoi + ", Email: " + email;
    }
}
