package com.pms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;

/*
Will be deprecated soon!!!
 */
@Document
public class Student {


    @Id
    private String studentID;

    private String fullName;

    private String password;

    private String email;

    private int DepartmentID = -1;

    public Student(){
        super();
    }

    public Student(String fullName, String password, String email) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.DepartmentID = departmentID;
    }
}
