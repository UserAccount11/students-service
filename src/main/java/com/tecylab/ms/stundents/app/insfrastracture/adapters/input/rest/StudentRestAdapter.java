package com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest;

import com.tecylab.ms.stundents.app.application.ports.input.StudentInputPort;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.mapper.StudentRestMapper;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.request.StudentCreateRequest;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.response.StudentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StudentRestAdapter {

  private final StudentInputPort inputPort;
  private final StudentRestMapper restMapper;

  @GetMapping("/v1/students")
  public List<StudentResponse> getAll() {
    return restMapper.toStudentResponses(inputPort.findAll());
  }

  @GetMapping("/v1/students/{id}")
  public StudentResponse findById(@PathVariable Long id) {
    return restMapper.toStudentResponse(inputPort.findById(id));
  }

  @PostMapping("/v1/students")
  public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentCreateRequest request) {
    StudentResponse response = restMapper.toStudentResponse(
        inputPort.save(restMapper.toStudent(request)));

    return ResponseEntity
        .created(URI.create("/api/v1/students/".concat(response.getId().toString())))
        .body(response);

//    return ResponseEntity.status(HttpStatus.CREATED)
//        .body(restMapper.toStudentResponse(
//            inputPort.save(restMapper.toStudent(request))));
  }

  @PutMapping("/v1/students/{id}")
  public StudentResponse update(@PathVariable Long id,
                                @Valid @RequestBody StudentCreateRequest request) {
    return restMapper.toStudentResponse(
        inputPort.update(id, restMapper.toStudent(request)));
  }

  @DeleteMapping("/v1/students/{id}")
  public void delete(@PathVariable Long id) {
    inputPort.deleteById(id);
  }

}
