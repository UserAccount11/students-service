package com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.repository;

import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, Long> {
}
