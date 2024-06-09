package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "detalles_facturas")
public class DetalleFactura {
	@Id
	@Column(name = "id_detalle")
	@GeneratedValue
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
	private int descuento;
	private float montoFinal;
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

	public DetalleFactura()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public Factura getFactura()
	{
		return this.factura;
	}

	public Concepto getConcepto()
	{
		return this.concepto;
	}

	public Alumno getAlumno()
	{
		return this.alumno;
	}

	public int getDescuento()
	{
		return this.descuento;
	}

	public float getMontoFinal()
	{
		return this.montoFinal;
	}

	public boolean isAbonado()
	{
		return this.abonado;
	}

	public Date getFechaPago()
	{
		return this.fechaPago;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setFactura(Factura factura)
	{
		this.factura = factura;
	}

	public void setConcepto(Concepto concepto)
	{
		this.concepto = concepto;
	}

	public void setAlumno(Alumno alumno)
	{
		this.alumno = alumno;
	}

	public void setDescuento(int descuento)
	{
		this.descuento = descuento;
	}

	public void setMontoFinal(float montoFinal)
	{
		this.montoFinal = montoFinal;
	}

	public void setAbonado(boolean abonado)
	{
		this.abonado = abonado;
	}

	public void setFechaPago(Date fechaPago)
	{
		this.fechaPago = fechaPago;
	}
}
