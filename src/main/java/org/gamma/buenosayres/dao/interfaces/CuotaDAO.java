package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Cuota;
import org.gamma.buenosayres.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CuotaDAO extends JpaRepository<Cuota, UUID> {
	Optional<Cuota> findTopByNivelOrderByFechaActualizacion(Nivel nivel);
}
