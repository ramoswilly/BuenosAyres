package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolDAO extends JpaRepository<Rol, Integer> {
	Optional<Rol> findByAuthority(String authority);
}
