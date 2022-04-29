package com.pms.service;

import com.mongodb.MongoException;
import com.pms.model.Payroll;
import com.pms.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class PayrollServiceAdapter implements PayrollService {
    private final PayrollRepository payrollRepository;

    @Override
    public Payroll addWeeklyData(Payroll user) {
        /*
        Extracting all the pay stubs by the user using UserID.
        This is to check if the user not accidentally being paid twice in the week!
         */
        List<Payroll> cache = payrollRepository.findByUserID(user.getUserID());
        if(cache.size() > 0) {
            for (Payroll x : cache) {
                log.info(x.getWeek() + ", " + user.getWeek());
                if (x.getWeek().equals(user.getWeek())) {
                    throw new MongoException("User has been paid for the week: "
                            + user.getWeek());
                }
            }
            log.info("Adding new pay stub for this week!");
        }
        else{
            log.info("New user!");
        }
        //Permitting to save in the repository.
        return payrollRepository.save(user);
    }

}
