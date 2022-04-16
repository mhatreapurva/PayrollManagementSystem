package com.pms.repository;

import com.pms.model.EnumRole;
import com.pms.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
    Role findByRolename(String rolename);
}
