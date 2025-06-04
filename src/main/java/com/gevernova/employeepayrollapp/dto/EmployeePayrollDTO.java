package com.gevernova.employeepayrollapp.dto;
import lombok.*;

import java.util.Objects;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class EmployeePayrollDTO {
    private String name;
    private long salary;


}