package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Curso;

import java.util.UUID;

public class ListarAlumnoDTO {
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private Curso curso;

	public ListarAlumnoDTO(UUID id, String dni, String nombre, String apellido, String direccion, Curso curso)
	{
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.curso = curso;
	}

	public ListarAlumnoDTO()
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

	public Curso getCurso()
	{
		return this.curso;
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

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public String toString()
	{
		return "ListarAlumnoDTO(id=" + this.getId() + ", dni=" + this.getDni() + ", nombre=" + this.getNombre() + ", apellido=" + this.getApellido() + ", direccion=" + this.getDireccion() + ", curso=" + this.getCurso() + ")";
	}
}