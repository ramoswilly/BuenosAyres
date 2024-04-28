package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlumnoDAO extends JpaRepository<Alumno, UUID> {
	Optional<Alumno> findAlumnoByPersona_Dni(String dni);
}
