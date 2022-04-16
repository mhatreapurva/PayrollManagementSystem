package com.pms.repository;

import com.pms.model.Role;
import com.pms.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,Long> {
    User findByEmail(String email);
}
