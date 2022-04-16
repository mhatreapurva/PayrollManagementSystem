package com.pms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Role {

    @Transient
    public static final String SEQUENCE_NAME = "roles_sequence";

    @Id
    private int id;

    private String rolename;

    public Role(){
        super();
    }

    public Role(String rolename) {
        this.rolename = rolename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
