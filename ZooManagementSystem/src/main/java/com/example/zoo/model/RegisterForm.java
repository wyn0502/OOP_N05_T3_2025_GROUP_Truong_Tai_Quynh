package com.example.zoo.model;

public class RegisterForm {

    private String fullname;
    private String username;
    private String role = "staff";   
    private String password;
    private String confirmPassword; 
    private String invite;           
    private String phone;

    public RegisterForm() {}

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getInvite() { return invite; }
    public void setInvite(String invite) { this.invite = invite; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
