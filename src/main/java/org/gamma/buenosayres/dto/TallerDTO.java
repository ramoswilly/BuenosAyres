package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Nivel;

import java.util.List;
import java.util.UUID;

public class TallerDTO {
	private UUID id;
	private String descripcion;
	private Nivel nivel;
	private List<UUID> alumnos;

	public TallerDTO(UUID id, String descripcion, Nivel nivel, List<UUID> alumnos)
	{
		this.id = id;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.alumnos = alumnos;
	}

	public TallerDTO()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public String getDescripcion()
	{
		return this.descripcion;
	}

	public Nivel getNivel()
	{
		return this.nivel;
	}

	public List<UUID> getAlumnos()
	{
		return this.alumnos;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}

	public void setAlumnos(List<UUID> alumnos)
	{
		this.alumnos = alumnos;
	}

	public String toString()
	{
		return "TallerDTO(id=" + this.getId() + ", descripcion=" + this.getDescripcion() + ", nivel=" + this.getNivel() + ", alumnos=" + this.getAlumnos() + ")";
	}
}
