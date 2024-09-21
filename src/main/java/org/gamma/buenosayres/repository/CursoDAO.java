package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.model.Alumno;
import org.gamma.buenosayres.model.Curso;
import org.gamma.buenosayres.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CursoDAO extends JpaRepository<Curso, UUID> {
	@Query("SELECT DISTINCT c " +
			"FROM Curso c " +
			"JOIN Materia m ON c.id = m.curso.id " +
			"JOIN Evaluacion e ON m.id = e.materia.id " +
			"JOIN Calificacion cal ON e.id = cal.evaluacion.id " +
			"WHERE cal.alumno = :alumno " +
			"ORDER BY c.nivel DESC, c.orden DESC")
	List<Curso> findByAlumno(@Param("alumno") Alumno alumno);
	@Query("SELECT c FROM Curso c WHERE c.nivel IN :niveles")
	List<Curso> findByNiveles(@Param("niveles") List<Nivel> niveles);
}
