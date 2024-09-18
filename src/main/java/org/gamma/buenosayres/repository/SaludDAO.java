package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.model.Salud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SaludDAO extends JpaRepository<Salud, UUID> {
	Optional<Salud> findByPersona(Persona persona);
}
