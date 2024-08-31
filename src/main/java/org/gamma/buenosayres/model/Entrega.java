package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "entregas")
public class Entrega {
	@Id
	@GeneratedValue
	private UUID id;
	@ManyToOne
	private Evaluacion evaluacion;
	@ManyToOne
	private Alumno alumno;
	private String comentarios;
	@Column(name = "fecha_entrega")
	private Date fecha;

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public Evaluacion getEvaluacion()
	{
		return evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion)
	{
		this.evaluacion = evaluacion;
	}

	public Alumno getAlumno()
	{
		return alumno;
	}

	public void setAlumno(Alumno alumno)
	{
		this.alumno = alumno;
	}

	public Date getFecha()
	{
		return fecha;
	}

	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}

	public String getComentarios()
	{
		return comentarios;
	}

	public void setComentarios(String comentarios)
	{
		this.comentarios = comentarios;
	}
}
