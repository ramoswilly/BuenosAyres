package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlumnoRepository extends JpaRepository<Alumno, UUID> {
	Optional<Alumno> findAlumnoByPersona_Dni(String dni);
	List<Alumno> findAllByPersona_Familia(Familia familia);
}
