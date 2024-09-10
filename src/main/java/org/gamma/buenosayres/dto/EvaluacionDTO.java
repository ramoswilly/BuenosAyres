package org.gamma.buenosayres.dto;

import java.time.LocalDate;
import java.util.UUID;

public class EvaluacionDTO {
	private UUID id;
	private String descripcion;
	private String comentarios;
	private LocalDate fechaCreacion;
	private LocalDate fechaVencimiento;
	private UUID idMateria;
	private UUID idProfesor;

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public String getComentarios()
	{
		return comentarios;
	}

	public void setComentarios(String comentarios)
	{
		this.comentarios = comentarios;
	}

	public LocalDate getFechaCreacion()
	{
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion)
	{
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDate getFechaVencimiento()
	{
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento)
	{
		this.fechaVencimiento = fechaVencimiento;
	}

	public UUID getIdMateria()
	{
		return idMateria;
	}

	public void setIdMateria(UUID idMateria)
	{
		this.idMateria = idMateria;
	}

	public UUID getIdProfesor()
	{
		return idProfesor;
	}

	public void setIdProfesor(UUID idProfesor)
	{
		this.idProfesor = idProfesor;
	}
}
