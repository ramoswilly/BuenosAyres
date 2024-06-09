package org.gamma.buenosayres.dto;

import java.util.List;
import java.util.UUID;

public class ActualizarFamiliaDTO {
	private UUID id;
	private String apellido;
	boolean habilitada;
	private List<UUID> miembros;

	public ActualizarFamiliaDTO(UUID id, String apellido, boolean habilitada, List<UUID> miembros)
	{
		this.id = id;
		this.apellido = apellido;
		this.habilitada = habilitada;
		this.miembros = miembros;
	}

	public ActualizarFamiliaDTO()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public String getApellido()
	{
		return this.apellido;
	}

	public boolean isHabilitada()
	{
		return this.habilitada;
	}

	public List<UUID> getMiembros()
	{
		return this.miembros;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public void setHabilitada(boolean habilitada)
	{
		this.habilitada = habilitada;
	}

	public void setMiembros(List<UUID> miembros)
	{
		this.miembros = miembros;
	}
}
