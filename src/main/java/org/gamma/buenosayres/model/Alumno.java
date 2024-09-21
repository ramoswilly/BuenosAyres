package org.gamma.buenosayres.model;

import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "alumnos")
public class Alumno {
	@Id
	@GeneratedValue
	@Column(name = "id_persona")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "id_persona")
	private Persona persona;
	@Column(name = "fecha_de_nacimiento")
	private LocalDate fechaNacimiento;
	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;
	private boolean habilitado;
	@ManyToMany
	@JoinTable(
			name = "alumnos-talleres",
			joinColumns = @JoinColumn(name = "id_alumno"),
			inverseJoinColumns = @JoinColumn(name = "id_taller"))
	private List<Taller> talleres;

	public Alumno()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public Persona getPersona()
	{
		return this.persona;
	}

	public Curso getCurso()
	{
		return this.curso;
	}

	public List<Taller> getTalleres()
	{
		return this.talleres;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setPersona(Persona persona)
	{
		this.persona = persona;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public void setTalleres(List<Taller> talleres)
	{
		this.talleres = talleres;
	}

	public LocalDate getFechaNacimiento()
	{
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento)
	{
		this.fechaNacimiento = fechaNacimiento;
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
