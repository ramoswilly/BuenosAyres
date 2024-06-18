package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Padre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PadreDAO extends JpaRepository<Padre, UUID> {
    Optional<Padre> findPadreByPersona_Dni(String dni);
}
