package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FamiliaDAO extends JpaRepository<Familia, UUID> {
}
