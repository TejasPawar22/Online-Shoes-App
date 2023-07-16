package com.cake.cakeadmin.Models;

import java.io.Serializable;

public class OrderDataModel implements Serializable {
    String OrderStatus;

    String Orderid;

    String token;
    String Userid;
    String currentDate;
    String currentTime;
    String productImg;
    String productName;
    String productPrice;

    String productQty;

    Long totalPrice;

    String userAddress;

    String userEmail;

    String userMobile;

    String userName;
    String userProfileImg;

    public OrderDataModel(){
    }




    public OrderDataModel(String orderStatus, String orderid, String token,String userid, String currentDate, String currentTime, String productImg, String productName, String productPrice, String productQty, Long totalPrice, String userAddress, String userEmail, String userMobile, String userName, String userProfileImg) {
        OrderStatus = orderStatus;
        Orderid = orderid;
        Userid = userid;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productImg = productImg;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.totalPrice = totalPrice;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userName = userName;
        this.userProfileImg = userProfileImg;
        this.token=token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderid() {
        return Orderid;
    }

    public void setOrderid(String orderid) {
        Orderid = orderid;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileImg() {
        return userProfileImg;
    }

    public void setUserProfileImg(String userProfileImg) {
        this.userProfileImg = userProfileImg;
    }



}
