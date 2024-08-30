package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "evaluaciones")
public class Evaluacion {
	@Id
	@GeneratedValue
	private UUID id;
	private String descripcion;
	private String comentarios;
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	@Column(name = "fecha_vencimiento")
	private Date fechaVencimiento;
	@ManyToOne
	private Materia materia;
	@ManyToOne
	private Profesor profesor;
	public void setId(UUID id)
	{
		this.id = id;
	}

	public UUID getId()
	{
		return id;
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

	public Materia getMateria()
	{
		return materia;
	}

	public void setMateria(Materia materia)
	{
		this.materia = materia;
	}

	public Profesor getProfesor()
	{
		return profesor;
	}

	public void setProfesor(Profesor profesor)
	{
		this.profesor = profesor;
	}
}
