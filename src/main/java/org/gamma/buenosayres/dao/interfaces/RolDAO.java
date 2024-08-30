package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Rol;
import org.gamma.buenosayres.model.TipoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolDAO extends JpaRepository<Rol, Integer> {
	Optional<Rol> findByAuthority(String authority);
}
