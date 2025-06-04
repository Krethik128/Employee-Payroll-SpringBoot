package com.gevernova.employeepayrollapp.services;

import com.gevernova.employeepayrollapp.dto.EmployeePayrollDTO;
import com.gevernova.employeepayrollapp.entity.EmployeeNotFoundException;
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;
import com.gevernova.employeepayrollapp.repositories.EmployeePayrollRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeePayrollService implements IEmployeePayrollService {

    private final EmployeePayrollRepository employeePayrollRepository; // Made final!

    // Spring automatically injects EmployeePayrollRepository here
    public EmployeePayrollService(EmployeePayrollRepository employeePayrollRepository) {
        this.employeePayrollRepository = employeePayrollRepository;
    }

    @Override
    public List<EmployeePayrollData> getAllEmployeePayrollData() {
        return employeePayrollRepository.findAll();
    }

    @Override
    public EmployeePayrollData getEmployeePayrollDataById(int empId) throws EmployeeNotFoundException {
        // Use orElseThrow to throw custom exception if not found
        return employeePayrollRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID: " + empId + " not found."));
    }

    @Override
    public EmployeePayrollData createEmployeePayrollData(EmployeePayrollDTO empPayrollDTO) {
        EmployeePayrollData employeePayrollData = new EmployeePayrollData(empPayrollDTO.getName(), empPayrollDTO.getSalary());
        return employeePayrollRepository.save(employeePayrollData);
    }

    @Override
    public EmployeePayrollData updateEmployeePayrollData(int empId, EmployeePayrollDTO empPayrollDTO) throws EmployeeNotFoundException {
        EmployeePayrollData existingEmployee = employeePayrollRepository.findById(empId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID: " + empId + " not found for update."));

        existingEmployee.setName(empPayrollDTO.getName());
        existingEmployee.setSalary(empPayrollDTO.getSalary());
        return employeePayrollRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployeePayrollData(int empId) throws EmployeeNotFoundException {
        // Check if employee exists before attempting to delete
        if (!employeePayrollRepository.existsById(empId)) {
            throw new EmployeeNotFoundException("Employee with ID: " + empId + " not found for deletion.");
        }
        employeePayrollRepository.deleteById(empId);
    }
}