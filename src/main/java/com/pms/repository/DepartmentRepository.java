package com.pms.repository;

import com.pms.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, Integer> {
    Department findByDepartmentName(String departmentName);
}
