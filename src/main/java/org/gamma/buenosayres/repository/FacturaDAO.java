package org.gamma.buenosayres.repository;

import org.gamma.buenosayres.dto.IngresosResponse;
import org.gamma.buenosayres.model.Factura;
import org.gamma.buenosayres.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FacturaDAO extends JpaRepository<Factura, UUID> {
	@Query("SELECT DISTINCT f.periodo FROM Factura f")
	List<LocalDate> obtenerPeriodos();
	List<Factura> findAllByPeriodo(LocalDate periodo);
    @Query("SELECT DISTINCT f FROM Factura f " +
           "JOIN f.detalles df " +
           "WHERE df.abonado = false " +
           "ORDER BY f.familia.id")
    List<Factura> facturasImpagas();
	@Query("SELECT f FROM Factura f WHERE f.periodo = (SELECT MAX(fa.periodo) FROM Factura fa) AND f.familia.habilitada = true ORDER BY f.montoFinal DESC")
	List<Factura> findTop10Familias();
	Optional<Factura> findByFamiliaAndPeriodo(Familia familia, LocalDate periodo);
	@Query("SELECT new org.gamma.buenosayres.dto.IngresosResponse(SUM(f.montoFinal), f.periodo) " +
			"FROM Factura f " +
			"GROUP BY f.periodo " +
			"ORDER BY f.periodo ASC"
	)
	List<IngresosResponse> ingresosPorMes();
}
