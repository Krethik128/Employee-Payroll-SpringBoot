package com.gevernova.employeepayrollapp.exceptionhandler;

import com.gevernova.employeepayrollapp.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.warning("Handling MethodArgumentNotValidException: " + exception.getMessage());
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        String errorMessage = errorList.stream()
                .map(ObjectError::getDefaultMessage) // Get the custom message you defined in DTO
                .collect(Collectors.joining(", ")); // Join multiple messages if present

        ResponseDTO responseDTO = new ResponseDTO("Validation Failed", errorMessage);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST); // HTTP 400
    }

    // Handles custom EmployeeNotFoundException
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        logger.warning("Handling EmployeeNotFoundException: " + exception.getMessage());
        ResponseDTO responseDTO = new ResponseDTO("Invalid Employee ID", exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND); // HTTP 404
    }

    // Generic handler for any other unhandled exceptions (optional but recommended)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGenericException(Exception exception) {
        logger.severe("Handling unhandled exception: " + exception.getMessage() + " Stack: " + exception.toString());
        ResponseDTO responseDTO = new ResponseDTO("Internal Server Error", "An unexpected error occurred.");
        // For security, avoid exposing raw exception messages in production for generic errors
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
    }
}
