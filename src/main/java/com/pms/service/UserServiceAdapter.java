/*
* This is the UserServiceadapter which implemts the UserService Interface ; this
* uses the adpater pattern.
*
* */




package com.pms.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.pms.model.Department;
import com.pms.model.Role;
import com.pms.model.SystemProperties;
import com.pms.model.User;
import com.pms.repository.DepartmentRepository;
import com.pms.repository.RoleRepository;
import com.pms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Service @Slf4j @RequiredArgsConstructor
public class UserServiceAdapter implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
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
        log.info("Trying to save new role to the database");

        //Check if role name exists in the database.
        Role cur = roleRepository.findByRolename(role.getRolename());

        if(Objects.isNull(cur)){
            /*
            Inserts new role in the database.
             */
            role.setId((int) sequenceGeneratorService.generateSequence(role.SEQUENCE_NAME));
            return roleRepository.save(role);
        }

        else if(!Objects.isNull(cur)){
            /*
            * Role name needs to be unique.
            * */
            throw new MongoException("Rolename is not unique.");
        }
        else {
            /*
            * Checks for any unexpected circumstances. A generic exception handler.
            * */
            throw  new MongoException("Unknown error, please check log.");
        }
    }

    @Override
    public Department saveDepartment(Department department) {
        log.info("Trying to add new department in the database.");

        Department cur = departmentRepository.findByDepartmentName(department.getDepartmentName());

        if(Objects.isNull(cur)) {
            department.setDepartmentID((int) sequenceGeneratorService.generateSequence(department.SEQUENCE_NAME));

            if((int)department.getManagerID() == SystemProperties.DEFAULT_MANAGER_ID){
                return departmentRepository.save(department);
            }

            else if (department.getManagerID() < 0){
                throw  new MongoException("Operation violates rules of schema!");
            }


            return departmentRepository.save(department);
        }
        else if (!Objects.isNull(cur)){
            throw new MongoException("Department name exists");
        }
        else{
            throw  new MongoException("Unknown error please check log");
        }
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

    @Override
    public List<User> getManagers(){
        log.info("Fetching all users with role: Manager");

        List<User> cache =  userRepository.findAll();
        List<User> manager = new ArrayList<>();
        for(User x : cache){
            List<String> strRoles = new ArrayList<>();
            for(Role r : x.getRoles()){
                strRoles.add(r.getRolename());
            }

            if(strRoles.contains("ROLE_MANAGER")) {
                x.setPassword("PROTECTED_INFORMATION");
                manager.add(x);
            }
        }
        return manager;
    }

    @Override
    public void refreshTokenService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                User user = getUser(email);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getRoles().stream().map(Role::getRolename).collect(Collectors.toList()))
                        .sign(algorithm);


                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch(Exception exception){

                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }


}
