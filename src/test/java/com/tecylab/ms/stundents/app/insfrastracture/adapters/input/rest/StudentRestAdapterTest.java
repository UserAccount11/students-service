package com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecylab.ms.stundents.app.application.ports.input.StudentInputPort;
import com.tecylab.ms.stundents.app.domain.model.Student;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.mapper.StudentRestMapper;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.response.StudentResponse;
import com.tecylab.ms.stundents.app.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentRestAdapter.class)
class StudentRestAdapterTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentInputPort inputPort;

  @MockBean
  private StudentRestMapper restMapper;

  private ObjectMapper objectMapper;
  private final Student student = TestUtils.buildStudentMock();
  private final StudentResponse studentResponse = TestUtils.buildStudentResponseMock();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    objectMapper = new ObjectMapper();
  }

  @Test
  void getAll() throws Exception {
    List<StudentResponse> responses = Collections.emptyList();
    when(inputPort.findAll()).thenReturn(Collections.emptyList());
    when(restMapper.toStudentResponses(anyList())).thenReturn(responses);

    ResultActions resultActions = mockMvc
        .perform(get("/api/v1/students")
        .contentType(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(responses)));

    verify(inputPort, times(1)).findAll();
    verify(restMapper, times(1)).toStudentResponses(anyList());
  }

  @Test
  void testFindById() throws Exception {
    when(inputPort.findById(anyLong()))
        .thenReturn(student);

    when(restMapper.toStudentResponse(any(Student.class)))
        .thenReturn(studentResponse);

    ResultActions resultActions = mockMvc
        .perform(get("/api/v1/students/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON));

    resultActions
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(studentResponse)));


    verify(inputPort, times(1)).findById(1L);
    verify(restMapper, times(1)).toStudentResponse(student);
  }


}
