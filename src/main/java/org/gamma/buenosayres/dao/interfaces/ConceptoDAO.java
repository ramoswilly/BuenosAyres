package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Concepto;
import org.gamma.buenosayres.model.TipoConcepto;
import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.Taller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ConceptoDAO extends JpaRepository<Concepto, UUID> {
	Optional<Concepto> findTopByNivelAndTipoDeConceptoOrderByFechaActualizacionDesc(Nivel nivel, TipoConcepto tipoConcepto);
	Optional<Concepto> findTopByTipoDeConceptoOrderByFechaActualizacionDesc(TipoConcepto tipoDeConcepto);
	Optional<Concepto> findTopByTallerOrderByFechaActualizacionDesc(Taller taller);

	@Query("SELECT c FROM Concepto c " +
           "WHERE (:tipo IS NULL OR c.tipoDeConcepto = :tipo) " +
           "AND (:nivel IS NULL OR c.nivel = :nivel) " +
           "AND (:taller IS NULL OR c.taller = :taller)" +
		   "ORDER BY c.fechaActualizacion DESC")
    Page<Concepto> findByTipoConceptoAndNivelAndTallerOrderByFechaActualizacionDesc(
            @Param("tipo") TipoConcepto tipo,
            @Param("nivel") Nivel nivel,
            @Param("taller") Taller taller,
            Pageable pageable);
}
