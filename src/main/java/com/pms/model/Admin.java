package com.pms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Admin {
    @Id
    private String id; //Primary key for the manager collection
    private String name;
    private String password;

}
