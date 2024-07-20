package com.tecylab.ms.stundents.app.utils;

import com.tecylab.ms.stundents.app.domain.model.Student;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.request.StudentCreateRequest;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.response.StudentResponse;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.entity.StudentEntity;

import java.time.LocalDateTime;

public class TestUtils {

  public static Student buildStudentMock() {
    return Student.builder()
        .id(1L)
        .firstname("Juan")
        .lastname("Perez")
        .age(18)
        .address("Calle 1")
        .build();
  }

  public static StudentEntity buildStudentEntityMock() {
    return StudentEntity.builder()
        .id(1L)
        .firstname("Juan")
        .lastname("Perez")
        .age(18)
        .address("Calle 1")
        .build();
  }

  public static StudentResponse buildStudentResponseMock() {
    return StudentResponse.builder()
        .id(1L)
        .firstname("Juan")
        .lastname("Perez")
        .age(18)
        .address("Calle 1")
        .timeStamp(LocalDateTime.now().toString())
        .build();
  }

  public static StudentCreateRequest buildStudentCreateRequestMock() {
    return StudentCreateRequest.builder()
        .firstname("Juan")
        .lastname("Perez")
        .age(18)
        .address("Calle 1")
        .build();
  }

}
