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

    public void setEmail() {
        this.email = email;
    }

    public String toString() {
        return "Họ tên: " + HoTen + ", Email: " + email;
    }

    public static void main(String[] args){
        user u = new user("Lò Tuấn Quỳnh", "24104502@st.phenikaa-uni.edu.vn");
        System.out.println(u);
}
}
