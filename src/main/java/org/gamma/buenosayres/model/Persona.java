package org.gamma.buenosayres.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "personas")
public class Persona {
	@Id
	@GeneratedValue
	@Column(name = "id_persona")
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	@Column(name = "email")
	private String email;
	private TipoPersona tipo;
	@ManyToOne
	@JoinColumn(name = "id_familia")
	private Familia familia;
	@OneToOne
	private Usuario usuario;
	public Persona(UUID id, String dni, String nombre, String apellido, String direccion, Familia familia)
	{
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.familia = familia;
	}

	public Persona()
	{
	}

	public UUID getId()
	{
		return this.id;
	}

	public String getDni()
	{
		return this.dni;
	}

	public String getNombre()
	{
		return this.nombre;
	}

	public String getApellido()
	{
		return this.apellido;
	}

	public String getDireccion()
	{
		return this.direccion;
	}

	public TipoPersona getTipo()
	{
		return this.tipo;
	}

	public Familia getFamilia()
	{
		return this.familia;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public void setDni(String dni)
	{
		this.dni = dni;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
	}

	public void setTipo(TipoPersona tipo)
	{
		this.tipo = tipo;
	}

	public void setFamilia(Familia familia)
	{
		this.familia = familia;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
}
