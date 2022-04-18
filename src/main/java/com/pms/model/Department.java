package com.pms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
/*

Department Class holds the information about all the departments in the university.

 */

@Document
public class Department {

    @Transient
    public static final String SEQUENCE_NAME = "departments_sequence";

    @Id
    private int departmentID;

    private String departmentName;

    private int managerID = -1;

    private double hourlyRate;

    public Department(String departmentName, int managerID, double hourlyRate) {
        this.departmentName = departmentName;
        this.managerID = managerID;
        this.hourlyRate = hourlyRate;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
