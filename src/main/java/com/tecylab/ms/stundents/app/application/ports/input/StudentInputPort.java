package com.tecylab.ms.stundents.app.application.ports.input;

import com.tecylab.ms.stundents.app.domain.model.Student;

import java.util.List;

public interface StudentInputPort {

  Student findById(Long id);
  List<Student> findAll();
  Student save(Student student);
  Student update(Long id, Student student);
  void deleteById(Long id);

}
