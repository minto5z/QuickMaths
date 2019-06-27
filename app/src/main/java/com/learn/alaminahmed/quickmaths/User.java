package com.learn.alaminahmed.quickmaths;

public class User {

    public String name,email,phoneNo;

    public String points,todayDate;

    public int todayPoint;

    public User(){

    }

    public User(String name, String email, String phoneNo,String points,String todayDate,int todayPoint) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.points = points;
        this.todayDate = todayDate;
        this.todayPoint = todayPoint;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public int getTodayPoint() {
        return todayPoint;
    }

    public void setTodayPoint(int todayPoint) {
        this.todayPoint = todayPoint;
    }
}
