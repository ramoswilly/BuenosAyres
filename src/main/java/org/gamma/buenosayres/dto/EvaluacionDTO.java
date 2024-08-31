package org.gamma.buenosayres.dto;

import java.util.Date;
import java.util.UUID;

public class EvaluacionDTO {
	private UUID id;
	private String descripcion;
	private String comentarios;
	private Date fechaCreacion;
	private Date fechaVencimiento;
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

	public Date getFechaCreacion()
	{
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion)
	{
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaVencimiento()
	{
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento)
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
