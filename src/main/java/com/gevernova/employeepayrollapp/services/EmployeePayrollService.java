package com.gevernova.employeepayrollapp.services;

import com.gevernova.employeepayrollapp.dto.EmployeeRequestDTO; // Using RequestDTO
import com.gevernova.employeepayrollapp.exceptionhandler.EmployeeNotFoundException;
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;
import com.gevernova.employeepayrollapp.mapper.EmployeeMapper;
import com.gevernova.employeepayrollapp.repositories.EmployeePayrollRepository; // Only this repository needed
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // For transactional operations

import java.util.List;
import java.util.logging.Logger;

@Service
public class EmployeePayrollService implements IEmployeePayrollService {

    private final EmployeePayrollRepository employeePayrollRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private static final Logger logger = Logger.getLogger(EmployeePayrollService.class.getName());

    // Only EmployeePayrollRepository is injected now
    public EmployeePayrollService(EmployeePayrollRepository employeePayrollRepository, RabbitMQProducer rabbitMQProducer) {
        this.employeePayrollRepository = employeePayrollRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    public List<EmployeePayrollData> getAllEmployeePayrollData() {
        List<EmployeePayrollData> employeeList = employeePayrollRepository.findAll();
        return employeeList;
    }

    @Override
    public EmployeePayrollData getEmployeePayrollDataById(int empId) throws EmployeeNotFoundException {
        EmployeePayrollData employeePayrollData = employeePayrollRepository.findById(empId)
                .orElseThrow(() -> {
                    logger.warning("Employee with ID: " + empId + " not found during retrieval attempt.");
                    return new EmployeeNotFoundException("Employee with ID: " + empId + " not found.");
                });
        return employeePayrollData;
    }

    @Override
    @Transactional // Ensures the save operation is transactional
    public EmployeePayrollData createEmployeePayrollData(EmployeeRequestDTO requestDTO) {
        // Map RequestDTO to EmployeePayrollData
        EmployeePayrollData employee = EmployeeMapper.toEntity(requestDTO);
        EmployeePayrollData savedEmployee = employeePayrollRepository.save(employee);

        // Send notification to RabbitMQ
        rabbitMQProducer.sendEmployeeCreationNotification(savedEmployee.getEmail(), savedEmployee.getName());
        return savedEmployee;
    }

    @Override
    @Transactional // Ensures the update operation is transactional
    public EmployeePayrollData updateEmployeePayrollData(int empId, EmployeeRequestDTO requestDTO) throws EmployeeNotFoundException {
        EmployeePayrollData existingEmployee = employeePayrollRepository.findById(empId)
                .orElseThrow(() -> {
                    return new EmployeeNotFoundException("Employee with ID: " + empId + " not found for update.");
                });
        String oldEmail = existingEmployee.getEmail();
        EmployeeMapper.updateEntityFromDTO(requestDTO, existingEmployee); // Update existingEmployee from requestDTO

        if(!oldEmail.equals(requestDTO.getEmail())) { //if email is also edited, then send email again
            logger.info("Email address changed for employee ID: " + empId + ". Sending update notification.");
            rabbitMQProducer.sendEmployeeUpdateNotification(requestDTO.getEmail(), requestDTO.getName());
        }

        return employeePayrollRepository.save(existingEmployee);
    }

    @Override
    @Transactional // Ensures the deletion is transactional
    public void deleteEmployeePayrollData(int empId) throws EmployeeNotFoundException {
        if (!employeePayrollRepository.existsById(empId)) {
            logger.warning("Employee with ID: " + empId + " not found for deletion.");
            throw new EmployeeNotFoundException("Employee with ID: " + empId + " not found for deletion.");
        }
        employeePayrollRepository.deleteById(empId);
    }
}