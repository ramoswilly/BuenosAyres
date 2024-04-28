package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Responsable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResponsableDAO extends JpaRepository<Responsable, UUID> {
}
