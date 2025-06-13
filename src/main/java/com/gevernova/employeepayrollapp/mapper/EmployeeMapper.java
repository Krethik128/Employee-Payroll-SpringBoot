package com.gevernova.employeepayrollapp.mapper;

import com.gevernova.employeepayrollapp.dto.EmployeeRequestDTO;
import com.gevernova.employeepayrollapp.dto.EmployeeResponseDTO;
import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    // Convert Request DTO → Entity
    public static EmployeePayrollData toEntity(EmployeeRequestDTO requestDTO) {
        return EmployeePayrollData.builder()
                .name(requestDTO.getName())
                .salary(requestDTO.getSalary())
                .phoneNumber(requestDTO.getPhoneNumber())
                .skills(requestDTO.getSkills() != null ?
                        List.copyOf(requestDTO.getSkills()) : List.of())
                .email(requestDTO.getEmail())
                .build();
    }

    // Convert Entity → Response DTO
    public static EmployeeResponseDTO toResponseDTO(EmployeePayrollData entity) {
        return EmployeeResponseDTO.builder()
                .employeeId(entity.getEmployeeId())
                .name(entity.getName())
                .salary(entity.getSalary())
                .phoneNumber(entity.getPhoneNumber())
                .skills(entity.getSkills() != null ?
                        List.copyOf(entity.getSkills()) : List.of())
                .email(entity.getEmail())
                .build();
    }

    // Update Entity from Request DTO
    public static void updateEntityFromDTO(EmployeeRequestDTO requestDTO, EmployeePayrollData entity) {

            entity.setName(requestDTO.getName());
            entity.setSalary(requestDTO.getSalary());
            entity.setPhoneNumber(requestDTO.getPhoneNumber());
            entity.setSkills(requestDTO.getSkills());
            entity.setEmail(requestDTO.getEmail());
    }

    // Convert List of Entities → List of Response DTOs
    public static List<EmployeeResponseDTO> toResponseDTOList(List<EmployeePayrollData> entities) {
        return entities.stream()
                .map(EmployeeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}

