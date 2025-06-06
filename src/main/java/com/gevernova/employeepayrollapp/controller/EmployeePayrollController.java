package com.gevernova.employeepayrollapp.controller;

import com.gevernova.employeepayrollapp.dto.EmployeeResponseDTO; // Import for outgoing DTO
import com.gevernova.employeepayrollapp.dto.EmployeeRequestDTO; // Import for incoming DTO
import com.gevernova.employeepayrollapp.dto.ResponseDTO; // Import for wrapper DTO
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData; // Still need this for internal service calls
import com.gevernova.employeepayrollapp.services.IEmployeePayrollService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors; // For mapping lists

@RestController
@RequestMapping("/employeepayroll")
public class EmployeePayrollController {

    private final IEmployeePayrollService employeePayrollService;
    private static final Logger logger = Logger.getLogger(EmployeePayrollController.class.getName());

    public EmployeePayrollController(IEmployeePayrollService employeePayrollService) {
        this.employeePayrollService = employeePayrollService;
        logger.info("EmployeePayrollController initialized via constructor injection.");
    }

    // Helper method to convert EmployeePayrollData to EmployeeResponseDTO
    private EmployeeResponseDTO mapToEmployeeResponseDTO(EmployeePayrollData employeePayrollData) {
        if (employeePayrollData == null) {
            return null;
        }
        return EmployeeResponseDTO.builder()
                .employeeId(employeePayrollData.getEmployeeId())
                .name(employeePayrollData.getName())
                .salary(employeePayrollData.getSalary())
                .phoneNumber(employeePayrollData.getPhoneNumber())
                .skills(employeePayrollData.getSkills())
                .build();
    }

    // --- Retrieve All Employees ---
    @GetMapping(value = {"", "/", "/get"})
    public ResponseEntity<ResponseDTO> getAllEmployeePayrollData() {
        logger.info("API Call: Received request to retrieve all employee records.");
        List<EmployeePayrollData> employeeList = employeePayrollService.getAllEmployeePayrollData();

        // Map list of EmployeePayrollData to list of EmployeeResponseDTO
        List<EmployeeResponseDTO> responseList = employeeList.stream()
                .map(this::mapToEmployeeResponseDTO)
                .collect(Collectors.toList());

        logger.info("API Call: Successfully retrieved " + responseList.size() + " employee records.");
        ResponseDTO responseDTO = new ResponseDTO("Retrieved all employees successfully!", responseList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // --- Retrieve Employee by ID ---
    @GetMapping("/get/{empId}")
    public ResponseEntity<ResponseDTO> getEmployeePayrollDataById(@PathVariable("empId") int empId) {
        logger.info("API Call: Received request to retrieve employee with ID: " + empId);
        EmployeePayrollData employee = employeePayrollService.getEmployeePayrollDataById(empId);

        // Map EmployeePayrollData to EmployeeResponseDTO
        EmployeeResponseDTO responseEmployee = mapToEmployeeResponseDTO(employee);

        logger.info("API Call: Successfully retrieved employee with ID: " + empId);
        ResponseDTO responseDTO = new ResponseDTO("Retrieved employee by ID successfully!", responseEmployee);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // --- Create New Employee with all details ---
    @PostMapping("/create") // Reusing the /create endpoint for full creation
    public ResponseEntity<ResponseDTO> createEmployeePayrollData(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
        logger.info("API Call: Received request to create new employee with details: " + requestDTO.getName());
        EmployeePayrollData employeePayrollData = employeePayrollService.createEmployeePayrollData(requestDTO);

        // Map EmployeePayrollData to EmployeeResponseDTO
        EmployeeResponseDTO responseEmployee = mapToEmployeeResponseDTO(employeePayrollData);

        logger.info("API Call: Successfully created employee with ID: " + employeePayrollData.getEmployeeId());
        ResponseDTO responseDTO = new ResponseDTO("Created employee successfully!", responseEmployee);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // --- Update Existing Employee with all details ---
    @PutMapping("/update/{empId}")
    public ResponseEntity<ResponseDTO> updateEmployeePayrollData(
            @PathVariable("empId") int empId,
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {
        logger.info("API Call: Received request to update employee with ID: " + empId + " with new details: " + requestDTO.getName());
        EmployeePayrollData employeePayrollData = employeePayrollService.updateEmployeePayrollData(empId, requestDTO);

        // Map EmployeePayrollData to EmployeeResponseDTO
        EmployeeResponseDTO responseEmployee = mapToEmployeeResponseDTO(employeePayrollData);

        logger.info("API Call: Successfully updated employee with ID: " + empId);
        ResponseDTO responseDTO = new ResponseDTO("Updated employee successfully!", responseEmployee);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // --- Delete Employee ---
    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<ResponseDTO> deleteEmployeePayrollData(@PathVariable("empId") int empId) {
        logger.info("API Call: Received request to delete employee with ID: " + empId);
        employeePayrollService.deleteEmployeePayrollData(empId);
        logger.info("API Call: Successfully deleted employee with ID: " + empId);
        ResponseDTO responseDTO = new ResponseDTO("Employee deleted successfully!", "Deleted employee with ID: " + empId);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}