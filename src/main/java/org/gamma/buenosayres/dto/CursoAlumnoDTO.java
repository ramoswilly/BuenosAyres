package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.Turno;

public class CursoAlumnoDTO {
	private int orden;
	private Nivel nivel;
	private Turno turno;

	public CursoAlumnoDTO(int orden, Nivel nivel, Turno turno)
	{
		this.orden = orden;
		this.nivel = nivel;
		this.turno = turno;
	}

	public CursoAlumnoDTO()
	{
	}

	public int getOrden()
	{
		return this.orden;
	}

	public Nivel getNivel()
	{
		return this.nivel;
	}

	public Turno getTurno()
	{
		return this.turno;
	}

	public void setOrden(int orden)
	{
		this.orden = orden;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}

	public void setTurno(Turno turno)
	{
		this.turno = turno;
	}
}
