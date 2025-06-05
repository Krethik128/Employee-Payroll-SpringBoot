package com.gevernova.employeepayrollapp.services;

import com.gevernova.employeepayrollapp.dto.RequestDTO;
import com.gevernova.employeepayrollapp.exceptionhandler.EmployeeNotFoundException;
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;
import com.gevernova.employeepayrollapp.repositories.EmployeePayrollRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class EmployeePayrollService implements IEmployeePayrollService {

    private final EmployeePayrollRepository employeePayrollRepository;
    // Initialize a Logger instance for this class
    private static final Logger logger = Logger.getLogger(EmployeePayrollService.class.getName());

    // Spring automatically injects EmployeePayrollRepository here using constructor injection
    public EmployeePayrollService(EmployeePayrollRepository employeePayrollRepository) {
        this.employeePayrollRepository = employeePayrollRepository;
        logger.info("EmployeePayrollService initialized with EmployeePayrollRepository.");
    }

    @Override
    public List<EmployeePayrollData> getAllEmployeePayrollData() {
        logger.info("Attempting to retrieve all employee records.");
        List<EmployeePayrollData> employeeList = employeePayrollRepository.findAll();
        logger.info("Successfully retrieved " + employeeList.size() + " employee records.");
        return employeeList;
    }

    @Override
    public EmployeePayrollData getEmployeePayrollDataById(int empId) throws EmployeeNotFoundException {
        logger.info("Attempting to retrieve employee with ID: " + empId);
        EmployeePayrollData employeePayrollData = employeePayrollRepository.findById(empId)
                .orElseThrow(() -> {
                    // Log a warning or error before throwing the exception
                    logger.warning("Employee with ID: " + empId + " not found during retrieval attempt.");
                    return new EmployeeNotFoundException("Employee with ID: " + empId + " not found.");
                });
        logger.info("Successfully retrieved employee with ID: " + empId + ": " + employeePayrollData.getName());
        return employeePayrollData;
    }

    @Override
    public EmployeePayrollData createEmployeePayrollData(RequestDTO empPayrollDTO) {
        logger.info("Attempting to create a new employee with name: " + empPayrollDTO.getName());
        EmployeePayrollData employeePayrollData = new EmployeePayrollData(empPayrollDTO.getName(), empPayrollDTO.getSalary());
        EmployeePayrollData savedEmployee = employeePayrollRepository.save(employeePayrollData);
        logger.info("Successfully created new employee with ID: " + savedEmployee.getEmployeeId() + " and name: " + savedEmployee.getName());
        return savedEmployee;
    }

    @Override
    public EmployeePayrollData updateEmployeePayrollData(int empId, RequestDTO empPayrollDTO) throws EmployeeNotFoundException {
        logger.info("Attempting to update employee with ID: " + empId + " to name: " + empPayrollDTO.getName());
        EmployeePayrollData existingEmployee = employeePayrollRepository.findById(empId)
                .orElseThrow(() -> {
                    // Log a warning before throwing the exception
                    logger.warning("Employee with ID: " + empId + " not found for update.");
                    return new EmployeeNotFoundException("Employee with ID: " + empId + " not found for update.");
                });

        // Update the existing employee's details
        existingEmployee.setName(empPayrollDTO.getName());
        existingEmployee.setSalary(empPayrollDTO.getSalary());
        EmployeePayrollData updatedEmployee = employeePayrollRepository.save(existingEmployee);
        logger.info("Successfully updated employee with ID: " + empId + " to name: " + updatedEmployee.getName());
        return updatedEmployee;
    }

    @Override
    public void deleteEmployeePayrollData(int empId) throws EmployeeNotFoundException {
        logger.info("Attempting to delete employee with ID: " + empId);
        // Check if employee exists before attempting to delete
        if (!employeePayrollRepository.existsById(empId)) {
            // Log a warning before throwing the exception
            logger.warning("Employee with ID: " + empId + " not found for deletion.");
            throw new EmployeeNotFoundException("Employee with ID: " + empId + " not found for deletion.");
        }
        employeePayrollRepository.deleteById(empId);
        logger.info("Successfully deleted employee with ID: " + empId);
    }
}