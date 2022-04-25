package com.pms.service;

import com.pms.model.Department;
import com.pms.model.Role;
import com.pms.model.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    Department saveDepartment(Department department);
    void addRoleToUser(String email, String rolename);
    User getUser(String email);
    List<User> getUsers();
    List<User> getManagers();
    void refreshTokenService(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
