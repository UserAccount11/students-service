package com.tecylab.ms.stundents.app.insfrastracture.adapters.input.rest.model.response;

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
public class StudentResponse {

  private String id;
  private String firstname;
  private String lastname;
  private Integer age;
  private String address;
  private String timeStamp;

}
