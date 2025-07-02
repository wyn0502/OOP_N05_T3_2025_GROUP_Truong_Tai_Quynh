public class user {
    private String HoTen;
    private String email;

    public user() {
    }

    public user(String HoTen, String email) {
        this.HoTen = HoTen;
        this.email = email;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(){
        this.email = email;
    }
}
