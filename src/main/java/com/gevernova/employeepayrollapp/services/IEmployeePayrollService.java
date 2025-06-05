package com.gevernova.employeepayrollapp.services;


import com.gevernova.employeepayrollapp.dto.RequestDTO;
import com.gevernova.employeepayrollapp.exceptionhandler.EmployeeNotFoundException;
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;

import java.util.List;

public interface IEmployeePayrollService {
    List<EmployeePayrollData> getAllEmployeePayrollData();
    EmployeePayrollData getEmployeePayrollDataById(int empId) throws EmployeeNotFoundException;
    EmployeePayrollData createEmployeePayrollData(RequestDTO empPayrollDTO);
    EmployeePayrollData updateEmployeePayrollData(int empId, RequestDTO empPayrollDTO) throws EmployeeNotFoundException;
    void deleteEmployeePayrollData(int empId) throws EmployeeNotFoundException;
}
