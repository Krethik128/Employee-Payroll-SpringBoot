// src/main/java/com/gevernova/employeepayrollapp/entity/EmployeePayrollData.java
package com.gevernova.employeepayrollapp.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor // Lombok generates a no-argument constructor
@AllArgsConstructor // Lombok generates a constructor with all fields (excluding static/transient)
@Builder // Lombok generates a builder pattern for object creation
@ToString // Lombok generates toString()
@EqualsAndHashCode // Lombok generates equals() and hashCode()
@Entity
@Table(name = "employee_payroll") // Specifies the main table name
public class EmployeePayrollData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private long salary;

    // --- Fields for personal details (directly in this entity) ---
    @Column(name = "phone_number")
    private String phoneNumber;

    @ElementCollection
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    private List<String> skills;

    @Column(name = "email")
    private String email;

    public EmployeePayrollData(String name, long salary, String phoneNumber, List<String> skills, String email) {
        this.name = name;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.skills = skills;
        this.email = email;
    }

    public EmployeePayrollData(String name, long salary) {
        this.name = name;
        this.salary = salary;
    }
}