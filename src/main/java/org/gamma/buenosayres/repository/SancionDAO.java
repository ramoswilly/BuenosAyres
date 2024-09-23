package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.model.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SancionDAO extends JpaRepository<Sancion, UUID> {
	List<Sancion> findAllByAlumno_Persona_Familia(Familia familia);
}