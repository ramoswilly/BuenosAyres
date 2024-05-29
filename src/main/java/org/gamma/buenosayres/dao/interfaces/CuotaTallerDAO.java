package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Concepto;
import org.gamma.buenosayres.model.CuotaTaller;
import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.Taller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CuotaTallerDAO extends JpaRepository<CuotaTaller, UUID> {
	Page<Concepto> findByTallerOrderByFechaActualizacionDesc(Taller taller, Pageable pageable);
}