package com.tecylab.ms.stundents.app.application.service;

import com.tecylab.ms.stundents.app.application.ports.input.StudentInputPort;
import com.tecylab.ms.stundents.app.application.ports.output.StudentPersistentPort;
import com.tecylab.ms.stundents.app.domain.exception.StudentNotFoundException;
import com.tecylab.ms.stundents.app.domain.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentInputPort {

  private final StudentPersistentPort persistentPort;

  @Override
  public Student findById(String id) {
    return persistentPort.findById(id)
        .orElseThrow(StudentNotFoundException::new);
  }

  @Override
  public List<Student> findAll() {
    return persistentPort.findAll();
  }

  @Override
  public Student save(Student student) {
    return persistentPort.save(student);
  }

  @Override
  public Student update(String id, Student student) {
    return persistentPort.findById(id)
        .map(savedStudent -> {
          savedStudent.setFirstname(student.getFirstname());
          savedStudent.setLastname(student.getLastname());
          savedStudent.setAge(student.getAge());
          savedStudent.setAddress(student.getAddress());
          return persistentPort.save(savedStudent);
        })
        .orElseThrow(StudentNotFoundException::new);
  }

  @Override
  public void deleteById(String id) {
    if (persistentPort.findById(id).isEmpty()) {
      throw new StudentNotFoundException();
    }
    persistentPort.deleteById(id);
  }
}
