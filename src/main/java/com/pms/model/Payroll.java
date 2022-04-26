package com.pms.model;


import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Payroll {
    private long userID;
    private String week;
    private double hoursWorked;

    public Payroll(long userID, String week, double hoursWorked) {
        this.userID = userID;
        this.week = week;
        this.hoursWorked = hoursWorked;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}
