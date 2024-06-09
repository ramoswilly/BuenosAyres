package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import org.gamma.buenosayres.dto.ConceptoDTO;
import org.gamma.buenosayres.mapper.ConceptoAcceptor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "conceptos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_de_concepto", discriminatorType = DiscriminatorType.STRING)
public abstract class Concepto implements ConceptoAcceptor<ConceptoDTO> {
	@Id
	@GeneratedValue
	@Column(name = "id_concepto")
	private UUID id;
	@Column(name = "monto")
	private float monto;
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;
	@Column(name = "tipo_de_concepto", insertable = false, updatable = false)
	String tipoDeConcepto;
	@Column(name = "nivel")
	@Enumerated(EnumType.STRING)
	private Nivel nivel;
	public Concepto(UUID id, float monto, Date fechaActualizacion)
	{
		this.id = id;
		this.monto = monto;
		this.fechaActualizacion = fechaActualizacion;
	}

	public Concepto()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public float getMonto()
	{
		return this.monto;
	}

	public Date getFechaActualizacion()
	{
		return this.fechaActualizacion;
	}

	public String getTipoDeConcepto()
	{
		return this.tipoDeConcepto;
	}

	public Nivel getNivel()
	{
		return this.nivel;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setMonto(float monto)
	{
		this.monto = monto;
	}

	public void setFechaActualizacion(Date fechaActualizacion)
	{
		this.fechaActualizacion = fechaActualizacion;
	}

	public void setTipoDeConcepto(String tipoDeConcepto)
	{
		this.tipoDeConcepto = tipoDeConcepto;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}
}