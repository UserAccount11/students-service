package com.tecylab.ms.stundents.app.application.ports.input;

import com.tecylab.ms.stundents.app.domain.model.Student;

import java.util.List;

public interface StudentInputPort {

  Student findById(String id);
  List<Student> findAll();
  Student save(Student student);
  Student update(String id, Student student);
  void deleteById(String id);

}
