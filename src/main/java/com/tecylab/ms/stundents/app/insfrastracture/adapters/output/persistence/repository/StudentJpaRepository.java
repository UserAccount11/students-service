package com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.repository;

import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.entity.StudentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJpaRepository extends MongoRepository<StudentEntity, String> {
}
