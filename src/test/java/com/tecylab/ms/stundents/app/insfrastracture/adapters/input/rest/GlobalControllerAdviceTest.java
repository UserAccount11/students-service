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

import static com.tecylab.ms.stundents.app.utils.ErrorCatalog.GENERIC_ERROR;
import static com.tecylab.ms.stundents.app.utils.ErrorCatalog.STUDENT_BAD_PARAMETERS;
import static com.tecylab.ms.stundents.app.utils.ErrorCatalog.STUDENT_NOT_FOUND;
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
  void whenStudentNotFoundException_thenReturnNotFound() throws Exception {
    when(inputPort.findById(anyLong()))
        .thenThrow(new StudentNotFoundException());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(result -> {
          ErrorResponse errorResponse = objectMapper.readValue(
              result.getResponse().getContentAsString(), ErrorResponse.class);
          assertThat(errorResponse.getCode()).isEqualTo(STUDENT_NOT_FOUND.getCode());
          assertThat(errorResponse.getMessage()).isEqualTo(STUDENT_NOT_FOUND.getMessage());
          assertThat(errorResponse.getTimestamp()).isNotNull();
        })
        .andDo(print());
  }

  @Test
  void whenMethodArgumentNotValidException_thenReturnBadRequest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(result -> {
          ErrorResponse errorResponse = objectMapper.readValue(
              result.getResponse().getContentAsString(), ErrorResponse.class);
          assertThat(errorResponse.getCode()).isEqualTo(STUDENT_BAD_PARAMETERS.getCode());
          assertThat(errorResponse.getMessage()).isEqualTo(STUDENT_BAD_PARAMETERS.getMessage());
          assertThat(errorResponse.getDetails()).isNotEmpty();
          assertThat(errorResponse.getTimestamp()).isNotNull();
        })
        .andDo(print());
  }

  @Test
  void whenGenericException_thenReturnInternalServerError() throws Exception {
    when(inputPort.findAll())
        .thenThrow(new RuntimeException("Generic error"));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError())
        .andExpect(result -> {
          ErrorResponse errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
          assertThat(errorResponse.getCode()).isEqualTo(GENERIC_ERROR.getCode());
          assertThat(errorResponse.getMessage()).isEqualTo(GENERIC_ERROR.getMessage());
          assertThat(errorResponse.getDetails().getFirst()).isEqualTo("Generic error");
          assertThat(errorResponse.getTimestamp()).isNotNull();
        })
        .andDo(print());
  }
}
