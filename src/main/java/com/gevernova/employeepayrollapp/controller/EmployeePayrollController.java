package com.gevernova.employeepayrollapp.controller;

import com.gevernova.employeepayrollapp.dto.RequestDTO;
import com.gevernova.employeepayrollapp.dto.ResponseDTO;
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;
import com.gevernova.employeepayrollapp.services.IEmployeePayrollService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employeepayroll")
public class EmployeePayrollController {

    private final IEmployeePayrollService employeePayrollService;
    private static final Logger logger = Logger.getLogger(EmployeePayrollController.class.getName());

    public EmployeePayrollController(IEmployeePayrollService employeePayrollService) {
        this.employeePayrollService = employeePayrollService;
        logger.info("EmployeePayrollController initialized via constructor injection.");
    }

    // --- Retrieve All Employees ---
    @GetMapping(value = {"", "/", "/get"})
    public ResponseEntity<ResponseDTO> getAllEmployeePayrollData() { // Changed return type to ResponseDTO
        logger.info("API Call: Received request to retrieve all employee records.");
        List<EmployeePayrollData> employeeList = employeePayrollService.getAllEmployeePayrollData();
        logger.info("API Call: Successfully retrieved " + employeeList.size() + " employee records.");
        ResponseDTO responseDTO = new ResponseDTO("Retrieved all employees successfully!", employeeList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // --- Retrieve Employee by ID ---
    @GetMapping("/get/{empId}")
    public ResponseEntity<ResponseDTO> getEmployeePayrollDataById(@PathVariable("empId") int empId) { // Changed return type
        logger.info("API Call: Received request to retrieve employee with ID: " + empId);
        // If EmployeeNotFoundException is thrown by service, it will be caught by global handler.
        EmployeePayrollData employee = employeePayrollService.getEmployeePayrollDataById(empId);
        logger.info("API Call: Successfully retrieved employee with ID: " + empId);
        ResponseDTO responseDTO = new ResponseDTO("Retrieved employee by ID successfully!", employee);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // --- Create New Employee ---
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createEmployeePayrollData(@Valid @RequestBody RequestDTO empPayrollDTO) {
        logger.info("API Call: Received request to create new employee with details: " + empPayrollDTO);
        // Validation is now handled by @Valid and caught by the global exception handler.
        EmployeePayrollData employeePayrollData = employeePayrollService.createEmployeePayrollData(empPayrollDTO);
        logger.info("API Call: Successfully created employee with ID: " + employeePayrollData.getEmployeeId());
        ResponseDTO responseDTO = new ResponseDTO("Created employee successfully!", employeePayrollData);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // --- Update Existing Employee ---
    @PutMapping("/update/{empId}")
    public ResponseEntity<ResponseDTO> updateEmployeePayrollData(
            @PathVariable("empId") int empId,
            @Valid @RequestBody RequestDTO empPayrollDTO) {
        logger.info("API Call: Received request to update employee with ID: " + empId + " with new details: " + empPayrollDTO);
        // Validation is now handled by @Valid and caught by the global exception handler.
        EmployeePayrollData employeePayrollData = employeePayrollService.updateEmployeePayrollData(empId, empPayrollDTO);
        logger.info("API Call: Successfully updated employee with ID: " + empId);
        ResponseDTO responseDTO = new ResponseDTO("Updated employee successfully!", employeePayrollData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // --- Delete Employee ---
    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<ResponseDTO> deleteEmployeePayrollData(@PathVariable("empId") int empId) {
        logger.info("API Call: Received request to delete employee with ID: " + empId);
        // EmployeeNotFoundException thrown by service will be caught by global handler.
        employeePayrollService.deleteEmployeePayrollData(empId);
        logger.info("API Call: Successfully deleted employee with ID: " + empId);
        ResponseDTO responseDTO = new ResponseDTO("Employee deleted successfully!", "Deleted employee with ID: " + empId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
