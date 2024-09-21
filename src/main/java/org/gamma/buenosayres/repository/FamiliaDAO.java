package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FamiliaDAO extends JpaRepository<Familia, UUID> {
}
