package com.pms.repository;

import com.pms.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, Integer> {
    Department findByDepartmentName(String departmentName);
}
