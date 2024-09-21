package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.time.LocalDate;
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
	private LocalDate fechaCreacion;
	@Column(name = "fecha_vencimiento")
	private LocalDate fechaVencimiento;
	private boolean habilitada;
	@ManyToOne
	private Materia materia;
	@ManyToOne
	private Profesor profesor;

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

	public boolean isHabilitada()
	{
		return habilitada;
	}

	public void setHabilitada(boolean habilitada)
	{
		this.habilitada = habilitada;
	}
}
