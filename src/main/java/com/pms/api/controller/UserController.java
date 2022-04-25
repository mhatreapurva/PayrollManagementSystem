package com.pms.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.model.Department;
import com.pms.model.Role;
import com.pms.model.User;
import com.pms.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.function.ServerResponse.created;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;


    @GetMapping("/managers")
    public ResponseEntity<List<User>> getManagers(){
        /*
        Returns a list of all the people who have their role as "manager".
         */

        return  ResponseEntity.ok().body(userService.getManagers());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        /*
        Returns list of all the employees. Only admins can do this.
         */
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/users/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/add-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        userService.addRoleToUser(form.getEmail(),form.getRolename());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/add-department")
    public ResponseEntity<?> addDepartment(@RequestBody Department department){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/admin/add-department").toUriString());
        return  ResponseEntity.created(uri).body(userService.saveDepartment(department));
    }


    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshTokenService(request,response);
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
//            try{
//                String refresh_token = authorizationHeader.substring("Bearer ".length());
//                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//                JWTVerifier verifier = JWT.require(algorithm).build();
//                DecodedJWT decodedJWT = verifier.verify(refresh_token);
//                String email = decodedJWT.getSubject();
//                User user = userService.getUser(email);
//
//                String access_token = JWT.create()
//                        .withSubject(user.getUsername())
//                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                        .withIssuer(request.getRequestURL().toString())
//                        .withClaim("roles",user.getRoles().stream().map(Role::getRolename).collect(Collectors.toList()))
//                        .sign(algorithm);
//
//
//                Map<String,String> tokens = new HashMap<>();
//                tokens.put("access_token",access_token);
//                tokens.put("refresh_token",refresh_token);
//                response.setContentType(APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
//
//            }catch(Exception exception){
//
//                response.setHeader("error",exception.getMessage());
//                response.setStatus(FORBIDDEN.value());
//                //response.sendError(FORBIDDEN.value());
//                Map<String,String> error = new HashMap<>();
//                error.put("error_message",exception.getMessage());
//                response.setContentType(APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(),error);
//            }
//        } else {
//            throw new RuntimeException("Refresh token is missing");
//        }
    }


}

@Data
class RoleToUserForm{
    private String email;
    private String rolename;
}


