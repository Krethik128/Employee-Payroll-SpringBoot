package com.gevernova.employeepayrollapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List; // Import List

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {
    private int employeeId; // Include the generated ID
    private String name;
    private long salary;
    private String phoneNumber;
    private List<String> skills;
}
