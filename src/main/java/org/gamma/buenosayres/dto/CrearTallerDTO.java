package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Nivel;

import java.util.UUID;

public class CrearTallerDTO {
	private String nombre;
	private Nivel nivel;
	private UUID profesorId;
	private String profesor;
	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public Nivel getNivel()
	{
		return nivel;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}

	public UUID getProfesorId()
	{
		return profesorId;
	}

	public void setProfesorId(UUID profesorId)
	{
		this.profesorId = profesorId;
	}

	public String getProfesor()
	{
		return profesor;
	}

	public void setProfesor(String profesor)
	{
		this.profesor = profesor;
	}
}
