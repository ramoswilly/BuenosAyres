package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profesores")
public class Profesor {
	@Id
	@GeneratedValue
	@Column(name = "id_persona")
	private UUID id;
	@OneToOne(cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "id_persona")
	private Persona persona;
	private String CUIL;
	private String telefono;
	private String email;
	private boolean habilitado;
	private Nivel nivel;
	private Date fechaNacimiento;
	private String banco;
	private String CBU;
	@Enumerated(EnumType.STRING)
	private TipoEmpleado tipo;
	@OneToMany(mappedBy = "responsable")
	private List<Curso> cursos;
	@OneToMany(mappedBy = "profesor")
	private List<Materia> materias;
	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public Persona getPersona()
	{
		return persona;
	}

	public void setPersona(Persona persona)
	{
		this.persona = persona;
	}

	public String getCUIL()
	{
		return CUIL;
	}

	public void setCUIL(String CUIL)
	{
		this.CUIL = CUIL;
	}

	public String getTelefono()
	{
		return telefono;
	}

	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public boolean isHabilitado()
	{
		return habilitado;
	}

	public void setHabilitado(boolean habilitado)
	{
		this.habilitado = habilitado;
	}

	public Nivel getNivel()
	{
		return nivel;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}

	public Date getFechaNacimiento()
	{
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento)
	{
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursos(List<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public List<Materia> getMaterias()
	{
		return materias;
	}

	public void setMaterias(List<Materia> materias)
	{
		this.materias = materias;
	}

	public TipoEmpleado getTipo()
	{
		return tipo;
	}

	public void setTipo(TipoEmpleado tipo)
	{
		this.tipo = tipo;
	}

	public String getBanco()
	{
		return banco;
	}

	public void setBanco(String banco)
	{
		this.banco = banco;
	}

	public String getCBU()
	{
		return CBU;
	}

	public void setCBU(String CBU)
	{
		this.CBU = CBU;
	}
}
