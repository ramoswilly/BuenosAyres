package org.gamma.buenosayres.dto;

import java.util.UUID;

public class CrearAlumnoDTO {
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private UUID curso;

	public CrearAlumnoDTO(String dni, String nombre, String apellido, String direccion, UUID curso)
	{
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.curso = curso;
	}

	public CrearAlumnoDTO()
	{
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

	public UUID getCurso()
	{
		return this.curso;
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

	public void setCurso(UUID curso)
	{
		this.curso = curso;
	}
}
