package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "facturas")
@NoArgsConstructor
@Getter
@Setter
public class Factura {
	@Id
	@Column(name = "id_factura")
	private UUID id;
	@ManyToOne
	@JoinColumn(name = "id_facturado")
	private Responsable facturado;
	@Column(name = "monto_final")
	private float montoFinal;
	@OneToMany(mappedBy = "factura")
	private List<DetalleFactura> detalles;

	public Factura(UUID id, Responsable facturado, float montoFinal, List<DetalleFactura> detalles)
	{
		this.id = id;
		this.facturado = facturado;
		this.montoFinal = montoFinal;
		this.detalles = detalles;
	}
}
