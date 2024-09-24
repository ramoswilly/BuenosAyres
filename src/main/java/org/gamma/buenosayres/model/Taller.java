package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "talleres")
public class Taller {
	@Id
	@GeneratedValue
	@Column(name = "id_taller")
	private UUID id;
	private String descripcion;
	@Enumerated(EnumType.STRING)
	private Nivel nivel;
	@ManyToMany(mappedBy = "talleres")
	private List<Alumno> alumnos;
	@ManyToOne
	@JoinColumn(name = "id_profesor")
	private Profesor profesor;
	public Taller()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public String getDescripcion()
	{
		return this.descripcion;
	}

	public Nivel getNivel()
	{
		return this.nivel;
	}

	public List<Alumno> getAlumnos()
	{
		return this.alumnos;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}

	public void setAlumnos(List<Alumno> alumnos)
	{
		this.alumnos = alumnos;
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
