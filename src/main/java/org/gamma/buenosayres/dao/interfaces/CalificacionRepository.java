package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Calificacion;
import org.gamma.buenosayres.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, UUID> {
	Optional<Calificacion> findByEvaluacionAndAlumno(Evaluacion evaluacion, Alumno alumno);
}
