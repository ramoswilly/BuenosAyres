package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.Turno;

import java.util.UUID;

public class CursoDTO {
	private UUID id;
	private int orden;
	private Nivel nivel;
	private Turno turno;
	private String id_responsable;
	private String nombre_responsable;
	private String apellido_responsable;
	private boolean habilitado;

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public int getOrden()
	{
		return orden;
	}

	public void setOrden(int orden)
	{
		this.orden = orden;
	}

	public Nivel getNivel()
	{
		return nivel;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}

	public Turno getTurno()
	{
		return turno;
	}

	public void setTurno(Turno turno)
	{
		this.turno = turno;
	}

	public String getId_responsable()
	{
		return id_responsable;
	}

	public void setId_responsable(String id_responsable)
	{
		this.id_responsable = id_responsable;
	}

	public String getNombre_responsable()
	{
		return nombre_responsable;
	}

	public void setNombre_responsable(String nombre_responsable)
	{
		this.nombre_responsable = nombre_responsable;
	}

	public String getApellido_responsable()
	{
		return apellido_responsable;
	}

	public void setApellido_responsable(String apellido_responsable)
	{
		this.apellido_responsable = apellido_responsable;
	}

	public boolean isHabilitado()
	{
		return habilitado;
	}

	public void setHabilitado(boolean habilitado)
	{
		this.habilitado = habilitado;
	}
}
