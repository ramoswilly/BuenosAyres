package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Concepto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConceptoDAO extends JpaRepository<Concepto, UUID> {
	List<Concepto> findAllBy(Pageable pageable);
	List<Concepto> findByTipoDeConcepto(String tipoDeConcepto, Pageable pageable);
}
