package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InasistenciaDAO extends JpaRepository<Inasistencia, UUID> {
}