package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface FacturaDAO extends JpaRepository<Factura, UUID> {
	@Query("SELECT DISTINCT f.periodo FROM Factura f")
	List<Date> obtenerPeriodos();
	List<Factura> findAllByPeriodo(Date periodo);
}
