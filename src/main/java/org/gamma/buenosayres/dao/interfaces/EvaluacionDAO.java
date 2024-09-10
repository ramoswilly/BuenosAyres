package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EvaluacionDAO extends JpaRepository<Evaluacion, UUID> {
	List<Evaluacion> findByMateriaOrderByFechaCreacion(Materia materia);
}
