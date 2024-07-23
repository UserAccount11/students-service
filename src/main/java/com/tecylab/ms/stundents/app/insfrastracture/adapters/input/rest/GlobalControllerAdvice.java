package com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest;

import com.tecylab.ms.stundents.app.domain.exception.StudentNotFoundException;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.response.ErrorResponse;
import com.tecylab.ms.stundents.app.utils.ErrorCatalog;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.tecylab.ms.stundents.app.utils.ErrorCatalog.STUDENT_BAD_PARAMETERS;
import static com.tecylab.ms.stundents.app.utils.ErrorCatalog.STUDENT_NOT_FOUND;

@RestControllerAdvice
public class GlobalControllerAdvice {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(StudentNotFoundException.class)
  public ErrorResponse handleStudentNotFoundException() {
    return ErrorResponse.builder()
        .code(STUDENT_NOT_FOUND.getCode())
        .message(STUDENT_NOT_FOUND.getMessage())
        .timestamp(LocalDateTime.now())
        .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();

    return ErrorResponse.builder()
        .code(STUDENT_BAD_PARAMETERS.getCode())
        .message(STUDENT_BAD_PARAMETERS.getMessage())
        .details(bindingResult.getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList())
        .timestamp(LocalDateTime.now())
        .build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ErrorResponse handleGenericException(Exception e) {
    return ErrorResponse.builder()
        .code(ErrorCatalog.GENERIC_ERROR.getCode())
        .message(ErrorCatalog.GENERIC_ERROR.getMessage())
        .details(Collections.singletonList(e.getMessage()))
        .timestamp(LocalDateTime.now())
        .build();
  }

}
