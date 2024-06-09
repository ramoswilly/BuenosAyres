package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DetalleFacturaDAO extends JpaRepository<DetalleFactura, UUID> {
}
