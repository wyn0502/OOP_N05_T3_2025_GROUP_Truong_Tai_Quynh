public class User {
    private String hovaten;
    private String email;
    public User(){

    }
public User(String hovaten, String email) {
    this.hovaten = hovaten;
    this.email = email;
    }
public void sethovaten(String hvt) {
    this.hovaten = hvt;
    }
public void setEmail(String email) {
    this.email = email;
    }
public String gethovaten() {
    return hovaten;
    }
public String getEmail() {
    return email;
    }
public String toString() {
        return "Ho ten: " + hovaten + ", Email: " + email;
    }
}
