package com.gevernova.employeepayrollapp.dto;

import lombok.Data; // Using Lombok's @Data for getters/setters/etc.
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String message;
    private Object data; // Can hold success data or error details
}
