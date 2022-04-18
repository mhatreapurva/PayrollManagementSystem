package com.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long id;
    private String name;
    private String username;

    @JsonIgnore
    private String password;
    private String email;

    private Collection<Role> roles = new ArrayList<>();

    public User(String name, String username, String password, String email, Collection<Role> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    /*
     * Custom function to return all the roles in the form of string
     * */

    public List<String> convertToString(User user){
        List<String> strRoles = new ArrayList<>();
        for(Role r : user.getRoles()){
            strRoles.add(r.getRolename());
        }
        return strRoles;
    }

}
