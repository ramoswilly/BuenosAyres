package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Materia;
import org.gamma.buenosayres.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MateriaDAO extends JpaRepository<Materia, UUID> {
	List<Materia> findByProfesor_Persona_Usuario(Usuario usuario);
}
