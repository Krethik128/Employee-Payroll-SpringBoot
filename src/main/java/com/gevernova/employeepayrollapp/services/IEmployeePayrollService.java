package com.gevernova.employeepayrollapp.services;


import com.gevernova.employeepayrollapp.dto.EmployeeRequestDTO;
import com.gevernova.employeepayrollapp.exceptionhandler.EmployeeNotFoundException;
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;

import java.util.List;

public interface IEmployeePayrollService {
    // Return EmployeePayrollData from service, let controller map to ResponseDTO
    List<EmployeePayrollData> getAllEmployeePayrollData();
    EmployeePayrollData getEmployeePayrollDataById(int empId) throws EmployeeNotFoundException;

    // All create/update operations now use RequestDTO
    EmployeePayrollData createEmployeePayrollData(EmployeeRequestDTO requestDTO);
    EmployeePayrollData updateEmployeePayrollData(int empId, EmployeeRequestDTO requestDTO) throws EmployeeNotFoundException;

    void deleteEmployeePayrollData(int empId) throws EmployeeNotFoundException;
}
