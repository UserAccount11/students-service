package com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence;

import com.tecylab.ms.stundents.app.application.ports.output.StudentPersistentPort;
import com.tecylab.ms.stundents.app.domain.model.Student;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.mapper.StudentPersistenceMapper;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.repository.StudentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentPersistenceAdapter implements StudentPersistentPort {

  private final StudentPersistenceMapper mapper;
  private final StudentJpaRepository repository;

  @Override
  public Optional<Student> findById(String id) {
    return repository.findById(id)
        .map(mapper::toStudent);
  }

  @Override
  public List<Student> findAll() {
    return mapper.toStudentList(repository.findAll());
  }

  @Override
  public Student save(Student student) {
    return mapper.toStudent(
        repository.save(mapper.toStudentEntity(student)));
  }

  @Override
  public void deleteById(String id) {
    repository.deleteById(id);
  }
}
