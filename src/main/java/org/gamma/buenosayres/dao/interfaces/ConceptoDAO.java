package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Concepto;
import org.gamma.buenosayres.model.Nivel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ConceptoDAO extends JpaRepository<Concepto, UUID> {
	Page<Concepto> findByTipoDeConcepto(String tipoDeConcepto, Pageable pageable);
	Page<Concepto> findByNivel(Nivel nivel, Pageable pageable);
	Page<Concepto> findByTipoDeConceptoAndNivel(String tipoDeConcepto, Nivel nivel, Pageable pageable);
}
