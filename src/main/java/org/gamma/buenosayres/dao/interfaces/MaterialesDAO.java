package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Materiales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MaterialesDAO extends JpaRepository<Materiales, UUID> {
	@Query("SELECT m FROM Materiales m ORDER BY m.fechaActualizacion DESC")
	Optional<Materiales> findTop();
}
