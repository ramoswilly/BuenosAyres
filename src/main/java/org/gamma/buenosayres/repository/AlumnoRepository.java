package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlumnoRepository extends JpaRepository<Alumno, UUID> {
	Optional<Alumno> findAlumnoByPersona_Dni(String dni);
}
