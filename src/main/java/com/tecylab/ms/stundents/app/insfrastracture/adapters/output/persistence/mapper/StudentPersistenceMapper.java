package com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.mapper;

import com.tecylab.ms.stundents.app.domain.model.Student;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.entity.StudentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentPersistenceMapper {

  StudentEntity toStudentEntity(Student student);

  Student toStudent(StudentEntity entity);

  List<Student> toStudentList(List<StudentEntity> entities);

}
