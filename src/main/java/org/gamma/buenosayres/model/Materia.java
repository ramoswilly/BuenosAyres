package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "materias")
public class Materia {
	@Id
	@GeneratedValue
	@Column(name = "id_materia")
	private UUID id;
	private String nombre;
	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;
	@ManyToOne
	@JoinColumn(name = "id_profesor")
	private Profesor profesor;

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

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
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
