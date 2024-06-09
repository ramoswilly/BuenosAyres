package org.gamma.buenosayres.dto;

import java.util.List;
import java.util.UUID;

public class ListarFamiliaDTO {
	private UUID id;
	private String apellido;
	private boolean habilitada;
	private List<MiembrosFamiliaDTO> miembros;

	public ListarFamiliaDTO(UUID id, String apellido, boolean habilitada, List<MiembrosFamiliaDTO> miembros)
	{
		this.id = id;
		this.apellido = apellido;
		this.habilitada = habilitada;
		this.miembros = miembros;
	}

	public ListarFamiliaDTO()
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

	public List<MiembrosFamiliaDTO> getMiembros()
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

	public void setMiembros(List<MiembrosFamiliaDTO> miembros)
	{
		this.miembros = miembros;
	}
}
