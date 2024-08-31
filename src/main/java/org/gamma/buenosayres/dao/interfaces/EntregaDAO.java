package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Entrega;
import org.gamma.buenosayres.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EntregaDAO extends JpaRepository<Entrega, UUID> {
	List<Entrega> findByEvaluacion(Evaluacion evaluacion);
}