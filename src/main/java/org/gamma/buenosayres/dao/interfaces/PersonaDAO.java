package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonaDAO extends JpaRepository<Persona, UUID> {
}
