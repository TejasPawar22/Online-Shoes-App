package com.cake.cakeadmin.Models;
public class userModel {
    String name;
    String email;
    String password;

    String address;
    String number;
    String profileImg;
    String Token;

    public userModel(){
    }



    public userModel(String name, String email, String address, String number, String profileImg, String Token){


    this.name = name;
    this.email = email;
    this.address=address;
    this.number=number;
    this.profileImg=profileImg;
    this.Token=Token;



}

    public userModel(String userName, String userEmail, String userPassword) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

}
