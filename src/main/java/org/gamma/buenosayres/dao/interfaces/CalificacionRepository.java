package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Calificacion;
import org.gamma.buenosayres.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, UUID> {
	Optional<Calificacion> findByEvaluacionAndAlumno(Evaluacion evaluacion, Alumno alumno);
	@Query("SELECT c FROM Calificacion c " +
				"JOIN c.evaluacion e " +
				"JOIN e.materia m " +
				"JOIN m.curso cu " +
				"WHERE c.alumno.id = :alumnoId " +
				"AND cu.id = :cursoId")
	List<Calificacion> findByAlumnoAndCurso(@Param("alumnoId") UUID alumnoId, @Param("cursoId") UUID cursoId);
}
