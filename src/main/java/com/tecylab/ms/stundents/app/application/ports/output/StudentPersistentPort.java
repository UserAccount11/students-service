package com.tecylab.ms.stundents.app.application.ports.output;

import com.tecylab.ms.stundents.app.domain.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentPersistentPort {

  Optional<Student> findById(Long id);
  List<Student> findAll();
  Student save(Student student);
  void deleteById(Long id);

}
