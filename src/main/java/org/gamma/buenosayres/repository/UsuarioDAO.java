package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, UUID> {
	Optional<Usuario> findByPersonaId(UUID id);
	Optional<Usuario> findByUsername(String username);
}
