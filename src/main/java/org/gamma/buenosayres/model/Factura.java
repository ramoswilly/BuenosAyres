package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.time.LocalDate;
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
	private LocalDate periodo;
	@ManyToOne
	@JoinColumn(name = "id_familia")
	private Familia familia;
	@ManyToOne
	@JoinColumn(name = "id_facturado")
	private Padre facturado;
	@Column(name = "monto_final")
	private float montoFinal;
	@OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
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

	public LocalDate getPeriodo()
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

	public void setPeriodo(LocalDate periodo)
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

	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

}
