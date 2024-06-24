package org.gamma.buenosayres.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.gamma.buenosayres.model.Nivel;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConceptoDTO {
	private UUID id;
	private float monto;
	private Date fechaActualizacion;
	private String tipoDeConcepto;
	private Nivel nivel;
	private UUID taller;
	private String descripcion;

	public ConceptoDTO(UUID id, float monto, Date fechaActualizacion, String tipoDeConcepto, Nivel nivel, UUID taller, String descripcion)
	{
		this.id = id;
		this.monto = monto;
		this.fechaActualizacion = fechaActualizacion;
		this.tipoDeConcepto = tipoDeConcepto;
		this.nivel = nivel;
		this.taller = taller;
		this.descripcion = descripcion;
	}

	public ConceptoDTO()
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

	public UUID getTaller()
	{
		return this.taller;
	}

	public String getDescripcion()
	{
		return this.descripcion;
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

	public void setTaller(UUID taller)
	{
		this.taller = taller;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public String toString()
	{
		return "ConceptoDTO(id=" + this.getId() + ", monto=" + this.getMonto() + ", fechaActualizacion=" + this.getFechaActualizacion() + ", tipoDeConcepto=" + this.getTipoDeConcepto() + ", nivel=" + this.getNivel() + ", taller=" + this.getTaller() + ", descripcion=" + this.getDescripcion() + ")";
	}
}
