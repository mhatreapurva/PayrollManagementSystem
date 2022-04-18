package com.pms.service;

import com.pms.model.Department;
import com.pms.model.Role;
import com.pms.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    Department saveDepartment(Department department);
    void addRoleToUser(String email, String rolename);
    User getUser(String email);
    List<User> getUsers();
    List<User> getManagers();
}
