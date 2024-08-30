package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "calificaciones")
public class Calificacion {
	@Id
	@GeneratedValue
	private UUID id;
	@ManyToOne
	private Evaluacion evaluacion;
	@ManyToOne
	private Alumno alumno;
	private float nota;

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

	public float getNota()
	{
		return nota;
	}

	public void setNota(float nota)
	{
		this.nota = nota;
	}
}
