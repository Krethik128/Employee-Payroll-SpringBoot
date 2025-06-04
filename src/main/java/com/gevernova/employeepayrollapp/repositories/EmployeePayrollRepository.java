package com.gevernova.employeepayrollapp.repositories;

import com.gevernova.employeepayrollapp.entity.EmployeePayrollData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this interface as a Spring Data JPA repository
public interface EmployeePayrollRepository extends JpaRepository<EmployeePayrollData, Integer> {
    // JpaRepository provides methods like save(), findById(), findAll(), deleteById(), etc.

}
