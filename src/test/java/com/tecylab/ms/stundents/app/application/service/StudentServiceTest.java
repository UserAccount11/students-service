package com.tecylab.ms.stundents.app.application.service;

import com.tecylab.ms.stundents.app.application.ports.output.StudentPersistentPort;
import com.tecylab.ms.stundents.app.domain.exception.StudentNotFoundException;
import com.tecylab.ms.stundents.app.domain.model.Student;
import com.tecylab.ms.stundents.app.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentPersistentPort persistentPort;

  @InjectMocks
  private StudentService studentService;

  @Test
  void testFindById_Success() {
    // Inicialización
    when(persistentPort.findById(anyLong()))
        .thenReturn(Optional.of(TestUtils.buildStudentMock()));

    // Comportamiento
    Student student = studentService.findById(1L);

    // Comprobaciones
    Assertions.assertNotNull(student);
    Assertions.assertEquals(1L, student.getId());
    Assertions.assertEquals("Juan", student.getFirstname());

    verify(persistentPort, times(1)).findById(1L);
  }

  @Test
  void testFindById_NotFound() {
    when(persistentPort.findById(anyLong()))
        .thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class, () -> studentService.findById(1L));

    verify(persistentPort, times(1)).findById(1L);
  }

  @Test
  void testFindAll() {
    when(persistentPort.findAll())
        .thenReturn(Collections.singletonList(TestUtils.buildStudentMock()));

    List<Student> students = studentService.findAll();

    assertFalse(students.isEmpty());
    assertEquals(1, students.size());
    assertEquals(1L, students.getFirst().getId());
    assertEquals("Juan", students.getFirst().getFirstname());

    verify(persistentPort, times(1)).findAll();
  }

  @Test
  void testSave() {
    Student student1 = new Student();

    when(persistentPort.save(any(Student.class)))
        .thenReturn(TestUtils.buildStudentMock());

    Student student = studentService.save(student1);

    assertNotNull(student);
    assertEquals(1L, student.getId());
    assertEquals("Juan", student.getFirstname());
    assertEquals("Perez", student.getLastname());
    assertEquals(18, student.getAge());
    assertEquals("Calle 1", student.getAddress());

    verify(persistentPort, times(1)).save(student1);

  }

  @Test
  void testUpdate_Success() {
    Student student = TestUtils.buildStudentMock();

    when(persistentPort.findById(anyLong()))
        .thenReturn(Optional.of(student));

    when(persistentPort.save(any(Student.class)))
        .thenReturn(student);

    student.setFirstname("Pablo");
    student.setLastname("Casas");

    Student actualStudent = studentService.update(1L, student);

    assertNotNull(actualStudent);

    assertEquals(1L, student.getId());
    assertEquals("Pablo", student.getFirstname());
    assertEquals("Casas", student.getLastname());

    verify(persistentPort, times(1)).findById(1L);
    verify(persistentPort, times(1)).save(student);
  }

  @Test
  void testUpdate_NotFound() {
    when(persistentPort.findById(anyLong()))
        .thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class,
        () -> studentService.update(1L, TestUtils.buildStudentMock()));

    verify(persistentPort, times(1)).findById(1L);
    verify(persistentPort, times(0)).save(TestUtils.buildStudentMock());
  }

  @Test
  void testDeleteById_Success() {
    when(persistentPort.findById(anyLong()))
        .thenReturn(Optional.of(TestUtils.buildStudentMock()));

    studentService.deleteById(1L);

    verify(persistentPort, times(1)).findById(1L);
    verify(persistentPort, times(1)).deleteById(1L);
  }

  @Test
  void testDeleteById_NotFound() {
    when(persistentPort.findById(anyLong()))
        .thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class,
        () -> studentService.deleteById(1L));

    verify(persistentPort, times(1)).findById(1L);
    verify(persistentPort, times(0)).deleteById(1L);
  }

}
