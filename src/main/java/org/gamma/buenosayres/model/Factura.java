package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "facturas")
public class Factura {
	@Id
	@GeneratedValue
	@Column(name = "id_factura")
	private UUID id;
	@Column(name = "periodo")
	private Date periodo;
	@ManyToOne
	@JoinColumn(name = "id_facturado")
	private Padre facturado;
	@Column(name = "monto_final")
	private float montoFinal;
	@OneToMany(mappedBy = "factura")
	private List<DetalleFactura> detalles;

	public Factura(UUID id, Padre facturado, float montoFinal, List<DetalleFactura> detalles)
	{
		this.id = id;
		this.facturado = facturado;
		this.montoFinal = montoFinal;
		this.detalles = detalles;
	}

	public Factura()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public Date getPeriodo()
	{
		return this.periodo;
	}

	public Padre getFacturado()
	{
		return this.facturado;
	}

	public float getMontoFinal()
	{
		return this.montoFinal;
	}

	public List<DetalleFactura> getDetalles()
	{
		return this.detalles;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setPeriodo(Date periodo)
	{
		this.periodo = periodo;
	}

	public void setFacturado(Padre facturado)
	{
		this.facturado = facturado;
	}

	public void setMontoFinal(float montoFinal)
	{
		this.montoFinal = montoFinal;
	}

	public void setDetalles(List<DetalleFactura> detalles)
	{
		this.detalles = detalles;
	}
}
