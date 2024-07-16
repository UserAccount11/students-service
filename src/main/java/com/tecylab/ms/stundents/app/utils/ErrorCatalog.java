package com.tecylab.ms.stundents.app.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {

  STUDENT_NOT_FOUND("ERR_STUDENT_001", "Student not found"),
  STUDENT_BAD_PARAMETERS("ERR_STUDENT_002", "Invalid parameters for creation student"),
  GENERIC_ERROR("ERR_STUDENT_GEN_001", "Internal server error");

  private final String code;
  private final String message;

}
