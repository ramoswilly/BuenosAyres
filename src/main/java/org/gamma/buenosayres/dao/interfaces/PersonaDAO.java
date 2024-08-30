package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonaDAO extends JpaRepository<Persona, UUID> {
	List<Persona> findPersonaByFamilia(Familia familia);
	Optional<Persona> findByDni(String dni);
}
