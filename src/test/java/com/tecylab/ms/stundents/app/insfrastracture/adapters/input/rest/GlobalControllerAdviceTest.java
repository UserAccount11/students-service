package com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecylab.ms.stundents.app.application.ports.input.StudentInputPort;
import com.tecylab.ms.stundents.app.domain.exception.StudentNotFoundException;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.mapper.StudentRestMapper;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(StudentRestAdapter.class)
class GlobalControllerAdviceTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private StudentInputPort inputPort;

  @MockBean
  private StudentRestMapper restMapper;

  @Test
  public void whenStudentNotFoundException_thenReturnNotFound() throws Exception {
    when(inputPort.findById(anyLong()))
        .thenThrow(new StudentNotFoundException());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(result -> {
          ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
          assertThat(errorResponse.getCode()).isEqualTo("ERR_STUDENT_001");
          assertThat(errorResponse.getMessage()).isEqualTo("Student not found");
        })
        .andDo(print());
  }

  @Test
  public void whenMethodArgumentNotValidException_thenReturnBadRequest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(result -> {
          ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
          assertThat(errorResponse.getCode()).isEqualTo("ERR_STUDENT_002");
          assertThat(errorResponse.getMessage()).isEqualTo("Invalid parameters for creation student");
        })
        .andDo(print());
  }

  @Test
  public void whenGenericException_thenReturnInternalServerError() throws Exception {
    when(inputPort.findAll())
        .thenThrow(new RuntimeException("Generic error"));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError())
        .andExpect(result -> {
          ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
          assertThat(errorResponse.getCode()).isEqualTo("ERR_STUDENT_GEN_001");
          assertThat(errorResponse.getMessage()).isEqualTo("Internal server error");
        })
        .andDo(print());
  }
}
