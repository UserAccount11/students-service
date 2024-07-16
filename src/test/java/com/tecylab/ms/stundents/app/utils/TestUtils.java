package com.tecylab.ms.stundents.app.utils;

import com.tecylab.ms.stundents.app.domain.model.Student;

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

}
