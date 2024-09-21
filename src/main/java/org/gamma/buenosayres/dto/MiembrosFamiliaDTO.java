package org.gamma.buenosayres.dto;

import java.util.UUID;

public class MiembrosFamiliaDTO {
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private String tipo;

	public MiembrosFamiliaDTO(UUID id, String dni, String nombre, String apellido, String direccion, String tipo)
	{
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.tipo = tipo;
	}

	public MiembrosFamiliaDTO()
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

	public String getTipo()
	{
		return this.tipo;
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

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
}
