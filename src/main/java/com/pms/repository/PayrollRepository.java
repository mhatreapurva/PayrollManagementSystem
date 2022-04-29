package com.pms.repository;

import com.pms.model.Payroll;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollRepository extends MongoRepository<Payroll, Long> {
    List<Payroll> findByUserID(long userID);
}
