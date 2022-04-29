package com.pms.api.controller;

import com.pms.model.Payroll;
import com.pms.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/*
Only Admins and Managers may access this resource.
Security config file ensures that.
 */

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollController {


    private final PayrollService payrollService;

    @PostMapping("/weekly-timesheet")
    public ResponseEntity<?> addWeeklyTimeSheet(@RequestBody Payroll payroll){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/payroll/weekly-timesheet").toUriString());
        return ResponseEntity.created(uri).body(payrollService.addWeeklyData(payroll));
    }

    @PostMapping("/analytics")
    public ResponseEntity<?> employeeStatistics(@RequestBody)



}
