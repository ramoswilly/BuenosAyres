package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MateriaDAO extends JpaRepository<Materia, UUID> {

}
