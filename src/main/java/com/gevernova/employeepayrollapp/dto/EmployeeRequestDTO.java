package com.gevernova.employeepayrollapp.dto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class EmployeeRequestDTO {

    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]{2,}$", message = "Employee name invalid: Must start with capital letter and have at least 3 characters (letters/spaces)")
    @NotBlank(message = "Employee name cannot be empty or null")
    private String name;

    @Min(value = 10000, message = "Salary must be at least 10000")
    private long salary;

    // --- New fields for personal details ---
    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Skills list cannot be null (can be empty list)")
    private List<String> skills; // List of skills

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email; // New field for email
}