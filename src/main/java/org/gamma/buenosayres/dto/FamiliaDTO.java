package org.gamma.buenosayres.dto;

import java.util.UUID;

public class FamiliaDTO {
	private UUID id;
	private String apellido;
	private boolean habilitada;

	public FamiliaDTO(UUID id, String apellido, boolean habilitada)
	{
		this.id = id;
		this.apellido = apellido;
		this.habilitada = habilitada;
	}

	public FamiliaDTO()
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
}
