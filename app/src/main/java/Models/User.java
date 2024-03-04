package Models;

public class User {
    private String fullname, email, cin , phone;

    public User() {
    }

    public User(String fullname, String email, String cin, String phone) {
        this.fullname = fullname;
        this.email = email;
        this.cin = cin;
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
