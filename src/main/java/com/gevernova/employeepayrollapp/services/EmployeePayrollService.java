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
        logger.info("EmployeePayrollService initialized.");
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
                    logger.warning("Employee with ID: " + empId + " not found during retrieval attempt.");
                    return new EmployeeNotFoundException("Employee with ID: " + empId + " not found.");
                });
        logger.info("Successfully retrieved employee with ID: " + empId + ": " + employeePayrollData.getName());
        return employeePayrollData;
    }

    @Override
    @Transactional // Ensures the save operation is transactional
    public EmployeePayrollData createEmployeePayrollData(EmployeeRequestDTO requestDTO) {
        logger.info("Attempting to create a new employee with details: " + requestDTO.getName());
        // Map RequestDTO to EmployeePayrollData
        EmployeePayrollData employee = EmployeeMapper.toEntity(requestDTO);
        EmployeePayrollData savedEmployee = employeePayrollRepository.save(employee);

        // Send notification to RabbitMQ
        rabbitMQProducer.sendEmployeeCreationNotification(savedEmployee.getEmail(), savedEmployee.getName());

        logger.info("Successfully created new employee with ID: " + savedEmployee.getEmployeeId() + " and name: " + savedEmployee.getName());
        return savedEmployee;
    }

    @Override
    @Transactional // Ensures the update operation is transactional
    public EmployeePayrollData updateEmployeePayrollData(int empId, EmployeeRequestDTO requestDTO) throws EmployeeNotFoundException {
        logger.info("Attempting to update employee with ID: " + empId + " with new details: " + requestDTO.getName());
        EmployeePayrollData existingEmployee = employeePayrollRepository.findById(empId)
                .orElseThrow(() -> {
                    logger.warning("Employee with ID: " + empId + " not found for update.");
                    return new EmployeeNotFoundException("Employee with ID: " + empId + " not found for update.");
                });

        EmployeeMapper.updateEntityFromDTO(requestDTO, existingEmployee); // Update existingEmployee from requestDTO

        logger.info("Successfully updated employee with ID: " + empId + " to name: " + existingEmployee.getName());
        return employeePayrollRepository.save(existingEmployee);
    }

    @Override
    @Transactional // Ensures the deletion is transactional
    public void deleteEmployeePayrollData(int empId) throws EmployeeNotFoundException {
        logger.info("Attempting to delete employee with ID: " + empId);
        if (!employeePayrollRepository.existsById(empId)) {
            logger.warning("Employee with ID: " + empId + " not found for deletion.");
            throw new EmployeeNotFoundException("Employee with ID: " + empId + " not found for deletion.");
        }
        employeePayrollRepository.deleteById(empId);
        logger.info("Successfully deleted employee with ID: " + empId);
    }
}