package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.Profesor;
import org.gamma.buenosayres.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
//TODO: Might be unstable rolandnivel
public interface ProfesorDAO extends JpaRepository<Profesor, UUID> {
	Optional<Profesor> findByPersonaDni(String dni);
	Optional<Profesor> findByPersona_Usuario_Username(String username);
	@Query("SELECT p FROM Profesor p " +
			"LEFT JOIN p.persona per " +
			"LEFT JOIN per.usuario u " +
			"LEFT JOIN u.roles r " +
			"WHERE (:rol IS NULL OR r.authority = :rol) " +
			"AND (:nivel IS NULL OR p.nivel = :nivel) ")
	List<Profesor> findProfesoresByRolAndNivel(
			@Param("rol") String rol,
			@Param("nivel") Nivel nivel);

}
