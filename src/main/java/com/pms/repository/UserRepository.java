package com.pms.repository;

import com.pms.model.Role;
import com.pms.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,Long> {
    User findByEmail(String email);
}
