package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.Turno;

import java.util.UUID;

public class MateriaDTO {
	private UUID id;
	private String nombre;
	private UUID id_curso;
	private int orden_curso;
	private Nivel nivel_curso;
	private Turno turno_curso;
	private UUID id_profesor;
	private String nombre_profesor;
	private String apellido_profesor;
	private boolean habilitada;
	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public UUID getId_curso()
	{
		return id_curso;
	}

	public void setId_curso(UUID id_curso)
	{
		this.id_curso = id_curso;
	}

	public UUID getId_profesor()
	{
		return id_profesor;
	}

	public void setId_profesor(UUID id_profesor)
	{
		this.id_profesor = id_profesor;
	}

	public String getNombre_profesor()
	{
		return nombre_profesor;
	}

	public void setNombre_profesor(String nombre_profesor)
	{
		this.nombre_profesor = nombre_profesor;
	}

	public String getApellido_profesor()
	{
		return apellido_profesor;
	}

	public void setApellido_profesor(String apellido_profesor)
	{
		this.apellido_profesor = apellido_profesor;
	}

	public int getOrden_curso()
	{
		return orden_curso;
	}

	public void setOrden_curso(int orden_curso)
	{
		this.orden_curso = orden_curso;
	}

	public Nivel getNivel_curso()
	{
		return nivel_curso;
	}

	public void setNivel_curso(Nivel nivel_curso)
	{
		this.nivel_curso = nivel_curso;
	}

	public Turno getTurno_curso()
	{
		return turno_curso;
	}

	public void setTurno_curso(Turno turno_curso)
	{
		this.turno_curso = turno_curso;
	}
}
