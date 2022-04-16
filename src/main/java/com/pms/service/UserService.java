package com.pms.service;

import com.pms.model.Role;
import com.pms.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String rolename);
    User getUser(String email);
    List<User> getUsers();
}
