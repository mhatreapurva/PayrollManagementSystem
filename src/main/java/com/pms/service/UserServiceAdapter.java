package com.pms.service;


import com.pms.model.Role;
import com.pms.model.User;
import com.pms.repository.RoleRepository;
import com.pms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service @Slf4j @RequiredArgsConstructor
public class UserServiceAdapter implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else{
            log.info("User found in the database: {}",email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRolename()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving user: {} to the database",user.getName());
        user.setId(sequenceGeneratorService.generateSequence(user.SEQUENCE_NAME));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role to the database");
        role.setId((int) sequenceGeneratorService.generateSequence(role.SEQUENCE_NAME));
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String rolename) {
        log.info("Saving/Updating role: {} user: {} to the database",rolename,email);
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByRolename(rolename);
        System.out.println(role.getId());
        System.out.println(role.getRolename());
        if(role != null) {
            System.out.println("Role name is: " + role.getRolename());
        }
        else{
            System.out.println("No role found");
        }
        user.getRoles().add(role);
        System.out.print("Hey there: ");

        for(Role x : user.getRoles()){
            System.out.println(x.getRolename());
        }
        userRepository.save(user);
    }

    @Override
    public User getUser(String email) {
        log.info("Fetching user: {}",email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }


}
