package com.Hyperfume.Backend.exception;

import com.Hyperfume.Backend.dto.request.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingException (Exception exception)
    {
        log.error("Error : {}", exception.getMessage());
        ApiResponse<?> apiResponse=new ApiResponse<>();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException (AppException exception)
    {
        ErrorCode errorCode=exception.getErrorCode();

        ApiResponse<?> apiResponse=new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception)
    {
        ApiResponse<?> apiResponse=new ApiResponse<>();

        ErrorCode errorCode=ErrorCode.INVALID_KEY;

        String enumKey = Objects.requireNonNull(exception.getFieldError().getDefaultMessage());
        try {
            errorCode=ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException e)
        {

        }
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
