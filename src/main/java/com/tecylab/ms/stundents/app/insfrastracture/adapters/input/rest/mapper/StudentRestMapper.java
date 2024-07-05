package com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.mapper;

import com.tecylab.ms.stundents.app.domain.model.Student;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.request.StudentCreateRequest;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentRestMapper {

  Student toStudent(StudentCreateRequest request);

  @Mapping(target = "timeStamp", expression = "java(mapTimeStamp())")
  StudentResponse toStudentResponse(Student student);

  List<StudentResponse> toStudentResponses(List<Student> students);

  default String mapTimeStamp() {
    return LocalDateTime.now().toString();
  }

}
