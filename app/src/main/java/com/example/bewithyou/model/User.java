package com.example.bewithyou.model;

public class User {
    private String  email;
    private String phoneNum;
    private String address;
    private String userName;

    public User() {
    }

    public User(String email, String phoneNum, String address, String userName) {
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", address='" + address + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
