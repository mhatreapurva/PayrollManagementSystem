package com.pms.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Payroll {
    /*
    This document will not be having any primary key as all the
    entries in this will be references from the user repositories.
     */
    private long userID;
    private String week;
    private double hoursWorked;
    private double hourlyWage;
    private double payAmount = 0;

    public Payroll(long userID, String week, double hoursWorked, double hourlyWage, double payAmount) {
        this.userID = userID;
        this.week = week;
        this.hoursWorked = hoursWorked;
        this.hourlyWage = hourlyWage;
        this.payAmount = payAmount;
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

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }
}
