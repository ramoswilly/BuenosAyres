package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Familia;
import org.gamma.buenosayres.model.Persona;
import org.gamma.buenosayres.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PersonaDAO extends JpaRepository<Persona, UUID> {
	List<Persona> findPersonaByFamilia(Familia familia);
	Optional<Persona> findByDni(String dni);

	@Query("SELECT p FROM Persona p " +
			"JOIN p.usuario u " +
			"JOIN u.roles r " +
			"WHERE r.authority IN :roles")
	List<Persona> findByRoles(@Param("roles") Set<String> roles);
}
