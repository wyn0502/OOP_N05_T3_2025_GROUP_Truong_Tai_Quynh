public class User {
    private String hoTen;
    private String email;
    private String diaChi;
    private String sdt;
    private String ngaySinh;
    private int tuoi;
    private String maSV;


    public User() {
    }


    public User(String hoTen, String email, String diaChi, String sdt, String ngaySinh, int tuoi, String maSV) {
        this.hoTen = hoTen;
        this.email = email;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.ngaySinh = ngaySinh;
        this.tuoi = tuoi;
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }


    public String toString() {
        return "Họ tên: " + hoTen +
               "\nEmail: " + email +
               "\nĐịa chỉ: " + diaChi +
               "\nSĐT: " + sdt +
               "\nNgày sinh: " + ngaySinh +
               "\nTuổi: " + tuoi +
               "\nMã sinh viên: " + maSV;
    }
}
