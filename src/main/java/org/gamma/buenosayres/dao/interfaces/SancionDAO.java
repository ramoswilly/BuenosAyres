package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SancionDAO extends JpaRepository<Sancion, UUID> {

}