package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "detalles_facturas")
@NoArgsConstructor
@Getter
@Setter
public class DetalleFactura {
	@Id
	@Column(name = "id_detalle")
	private UUID id;
	@ManyToOne
	@JoinColumn(name = "id_factura")
	private Factura factura;
	@ManyToOne
	@JoinColumn(name = "id_concepto")
	private Concepto concepto;
	@ManyToOne
	@JoinColumn(name = "id_alumno")
	private Alumno alumno;
	private boolean abonado;
	private Date fechaPago;

	public DetalleFactura(UUID id, Factura factura, Concepto concepto, boolean abonado, Date fechaPago)
	{
		this.id = id;
		this.factura = factura;
		this.concepto = concepto;
		this.abonado = abonado;
		this.fechaPago = fechaPago;
	}
}
