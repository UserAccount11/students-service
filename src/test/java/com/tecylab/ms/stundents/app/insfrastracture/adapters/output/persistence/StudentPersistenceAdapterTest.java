package com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence;

import com.tecylab.ms.stundents.app.domain.model.Student;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.entity.StudentEntity;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.mapper.StudentPersistenceMapper;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.repository.StudentJpaRepository;
import com.tecylab.ms.stundents.app.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentPersistenceAdapterTest {

  @Mock
  private StudentPersistenceMapper mapper;

  @Mock
  private StudentJpaRepository repository;

  @InjectMocks
  private StudentPersistenceAdapter studentPersistenceAdapter;

  private final Student student = TestUtils.buildStudentMock();
  private final StudentEntity studentEntity = TestUtils.buildStudentEntityMock();

  @Test
  void testFindById_Success() {
    when(repository.findById(anyLong()))
        .thenReturn(Optional.of(studentEntity));

    when(mapper.toStudent(any(StudentEntity.class)))
        .thenReturn(student);

    Optional<Student> studentFounded = studentPersistenceAdapter.findById(1L);

    assertTrue(studentFounded.isPresent());
    assertEquals(student, studentFounded.get());

    verify(repository, times(1)).findById(1L);
    verify(mapper, times(1)).toStudent(studentEntity);
  }

  @Test
  void testFindById_NotFound() {
    when(repository.findById(anyLong()))
        .thenReturn(Optional.empty());

    Optional<Student> studentFounded = studentPersistenceAdapter.findById(1L);

    assertFalse(studentFounded.isPresent());

    verify(repository, times(1)).findById(1L);
    verify(mapper, times(0)).toStudent(studentEntity);
  }

  @Test
  void testFindAll() {
    List<StudentEntity> entities = Collections.singletonList(studentEntity);
    List<Student> students = Collections.singletonList(student);

    when(repository.findAll()).thenReturn(entities);
    when(mapper.toStudentList(anyList())).thenReturn(students);

    List<Student> studentsFounded = studentPersistenceAdapter.findAll();

    assertNotNull(studentsFounded);
    assertEquals(1, studentsFounded.size());
    assertEquals(students, studentsFounded);

    verify(repository, times(1)).findAll();
    verify(mapper, times(1)).toStudentList(entities);
  }

  @Test
  void testSave() {
    when(mapper.toStudentEntity(any(Student.class)))
        .thenReturn(studentEntity);

    when(repository.save(any(StudentEntity.class)))
        .thenReturn(studentEntity);

    when(mapper.toStudent(any(StudentEntity.class)))
        .thenReturn(student);

    Student studentSaved = studentPersistenceAdapter.save(student);

    assertNotNull(studentSaved);
    assertEquals(student, studentSaved);

    verify(mapper, times(1)).toStudentEntity(student);
    verify(repository, times(1)).save(studentEntity);
    verify(mapper, times(1)).toStudent(studentEntity);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(anyLong());

    studentPersistenceAdapter.deleteById(1L);

    verify(repository, times(1)).deleteById(1L);
  }

}
