package com.gevernova.employeepayrollapp.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class RequestDTO {

    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]{2,}$", message = "Employee name invalid: Must start with capital letter and have at least 3 characters (letters/spaces)")
    @NotBlank(message = "First name is a mandatory field")
    private String name;

    @NotNull(message = "Salary is a mandatory field")
    @Min(value = 10000, message = "Salary must be greater than or equal to 10000")
    private long salary;


}