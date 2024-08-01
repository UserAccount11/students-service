package com.tecylab.ms.stundents.app;

import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.entity.StudentEntity;
import com.tecylab.ms.stundents.app.insfrastracture.adapters.output.persistence.repository.StudentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class StudentsServiceApplication implements CommandLineRunner {

	private final StudentJpaRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StudentsServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.saveAll(Arrays.asList(
				new StudentEntity(null, "Carlos", "Campos", 20, "Dirección 1"),
				new StudentEntity(null, "José", "Lopez", 20, "Dirección 2"),
				new StudentEntity(null, "Anita", "Ruiz", 20, "Dirección 3"),
				new StudentEntity(null, "Fatima", "Segovia", 20, "Dirección 4"),
				new StudentEntity(null, "Fernanda", "Arias", 20, "Dirección 5")));
	}

}
