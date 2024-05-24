package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Concepto;
import org.gamma.buenosayres.model.Nivel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConceptoDAO extends JpaRepository<Concepto, UUID> {
	Page<Concepto> findByTipoDeConceptoOrderByFechaActualizacionDesc(String tipoDeConcepto, Pageable pageable);
	Page<Concepto> findByNivelOrderByFechaActualizacionDesc(Nivel nivel, Pageable pageable);
	Page<Concepto> findByTipoDeConceptoAndNivelOrderByFechaActualizacionDesc(String tipoDeConcepto, Nivel nivel, Pageable pageable);
}
