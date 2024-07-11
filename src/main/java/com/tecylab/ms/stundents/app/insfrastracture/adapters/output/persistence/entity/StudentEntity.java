package com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Getter
@Setter
@Document(collection = "students")
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {

  @Id
  private String id;
  private String firstname;
  private String lastname;
  private Integer age;
  private String address;

}
