package com.pms;

import com.pms.model.Role;
import com.pms.model.Student;
import com.pms.model.User;
import com.pms.repository.StudentRepository;
import com.pms.repository.UserRepository;
import com.pms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.pms")
@EnableMongoRepositories
public class PmsApplication {
	@Autowired
	StudentRepository studentRepository;
	public static void main(String[] args) {

		SpringApplication.run(PmsApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner run(UserService userService, UserRepository userRepository){
//		return args ->{
//			userRepository.deleteAll();
//			userService.saveRole(new Role("ROLE_STUDENT"));
//			userService.saveRole(new Role("ROLE_MANAGER"));
//			userService.saveRole(new Role("ROLE_ADMIN"));
//
//			userService.saveUser(new User("a","a","1234","a@gmail.com",new ArrayList<>()));
//			userService.saveUser(new User("b","b","1234","b@gmail.com",new ArrayList<>()));
//			userService.saveUser(new User("c","c","1234","c@gmail.com",new ArrayList<>()));
//			userService.saveUser(new User("d","d","1234","d@gmail.com",new ArrayList<>()));
//			userService.saveUser(new User("e","e","1234","e@gmail.com",new ArrayList<>()));
//
//			userService.addRoleToUser("a@gmail.com","ROLE_STUDENT");
//			userService.addRoleToUser("b@gmail.com","ROLE_STUDENT");
//			userService.addRoleToUser("c@gmail.com","ROLE_STUDENT");
//			userService.addRoleToUser("d@gmail.com","ROLE_STUDENT");
//			userService.addRoleToUser("e@gmail.com","ROLE_MANAGER");
//			userService.addRoleToUser("e@gmail.com","ROLE_ADMIN");
//
//		};
//	}


}
