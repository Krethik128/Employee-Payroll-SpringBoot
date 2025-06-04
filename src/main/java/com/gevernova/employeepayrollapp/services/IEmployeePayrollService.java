package com.gevernova.employeepayrollapp.services;


import com.gevernova.employeepayrollapp.dto.EmployeePayrollDTO;
import com.gevernova.employeepayrollapp.entity.EmployeeNotFoundException;
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;

import java.util.List;

public interface IEmployeePayrollService {
    List<EmployeePayrollData> getAllEmployeePayrollData();
    EmployeePayrollData getEmployeePayrollDataById(int empId) throws EmployeeNotFoundException;
    EmployeePayrollData createEmployeePayrollData(EmployeePayrollDTO empPayrollDTO);
    EmployeePayrollData updateEmployeePayrollData(int empId, EmployeePayrollDTO empPayrollDTO) throws EmployeeNotFoundException;
    void deleteEmployeePayrollData(int empId) throws EmployeeNotFoundException;
}
