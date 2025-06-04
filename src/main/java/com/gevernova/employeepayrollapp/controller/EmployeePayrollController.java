package com.gevernova.employeepayrollapp.controller;

import com.gevernova.employeepayrollapp.dto.EmployeePayrollDTO;
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;
import com.gevernova.employeepayrollapp.services.IEmployeePayrollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeepayroll")
public class EmployeePayrollController {

    private final IEmployeePayrollService employeePayrollService;

    public EmployeePayrollController(IEmployeePayrollService employeePayrollService) {
        this.employeePayrollService = employeePayrollService;
    }

    @GetMapping(value = {"", "/", "/get"})
    public ResponseEntity<List<EmployeePayrollData>> getEmployeePayrollData() {
        List<EmployeePayrollData> employeeList = employeePayrollService.getAllEmployeePayrollData();
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/get/{empId}")
    public ResponseEntity<EmployeePayrollData> getEmployeePayrollData(@PathVariable("empId") int empId) {
        // if employee not present it automatically map to 404 due to @ResponseStatus.
        EmployeePayrollData employee = employeePayrollService.getEmployeePayrollDataById(empId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeePayrollData> createEmployeePayrollData(@RequestBody EmployeePayrollDTO empPayrollDTO) {
        EmployeePayrollData employeePayrollData = employeePayrollService.createEmployeePayrollData(empPayrollDTO);
        return new ResponseEntity<>(employeePayrollData, HttpStatus.CREATED);
    }

    @PutMapping("/update/{empId}")
    public ResponseEntity<EmployeePayrollData> updateEmployeePayrollData(@PathVariable("empId") int empId,
                                                                         @RequestBody EmployeePayrollDTO empPayrollDTO) {
        EmployeePayrollData employeePayrollData = employeePayrollService.updateEmployeePayrollData(empId, empPayrollDTO);
        return new ResponseEntity<>(employeePayrollData, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<String> deleteEmployeePayrollData(@PathVariable("empId") int empId) {
        // If the service throws EmployeeNotFoundException, Spring handles it.
        employeePayrollService.deleteEmployeePayrollData(empId);
        return new ResponseEntity<>("Deleted employee with ID: " + empId, HttpStatus.OK);
    }
}
