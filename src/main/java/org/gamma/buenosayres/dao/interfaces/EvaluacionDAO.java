package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Evaluacion;
import org.gamma.buenosayres.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EvaluacionDAO extends JpaRepository<Evaluacion, UUID> {
	List<Evaluacion> findByMateriaOrderByFechaCreacion(Materia materia);
	@Query("SELECT e FROM Evaluacion e " +
			"WHERE e.materia = :materia " +
			"AND e.fechaVencimiento IS NOT NULL " + // Filtrar por evaluaciones con fecha de vencimiento
			"AND e.fechaVencimiento >= :hoy " + // Filtrar por fecha de vencimiento no vencida
			"ORDER BY e.fechaCreacion")
	List<Evaluacion> findEntregablesByMateria(@Param("materia") Materia materia, @Param("hoy") LocalDate hoy);
}
