package org.gamma.buenosayres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cursos")
public class Curso {
	@Id
	@Column(name = "id_curso")
	private UUID id;
	private int orden;
	@Enumerated(EnumType.STRING)
	private Nivel nivel;
	@Enumerated(EnumType.STRING)
	private Turno turno;
	@ManyToOne
	@JoinColumn(name = "id_profesor")
	@JsonIgnore
	private Profesor responsable;
	@OneToMany(mappedBy = "curso")
	@JsonIgnore
	private List<Alumno> alumnos;
	@ColumnDefault("true")
	private boolean habilitado;
	public Curso(UUID id, int orden, Nivel nivel, Turno turno, List<Alumno> alumnos)
	{
		this.id = id;
		this.orden = orden;
		this.nivel = nivel;
		this.turno = turno;
		this.alumnos = alumnos;
	}

	public Curso()
	{
	}

	public UUID getId()
	{
		return this.id;
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

	public List<Alumno> getAlumnos()
	{
		return this.alumnos;
	}

	public void setId(UUID id)
	{
		this.id = id;
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

	@JsonIgnore
	public void setAlumnos(List<Alumno> alumnos)
	{
		this.alumnos = alumnos;
	}

	public Profesor getResponsable()
	{
		return responsable;
	}

	public void setResponsable(Profesor responsable)
	{
		this.responsable = responsable;
	}

	public boolean isHabilitado()
	{
		return habilitado;
	}

	public void setHabilitado(boolean habilitado)
	{
		this.habilitado = habilitado;
	}
}
