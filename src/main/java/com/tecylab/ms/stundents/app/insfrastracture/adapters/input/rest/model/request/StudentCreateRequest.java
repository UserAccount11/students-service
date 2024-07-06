package com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateRequest {

  @NotBlank(message = "Field firstname cannot be empty or null")
  private String firstname;

  @NotBlank(message = "Field lastname cannot be empty or null")
  private String lastname;

  @NotNull(message = "Field age cannot be null")
  @Min(value = 1, message = "Field age greater than zero")
  private Integer age;

  private String address;

}
