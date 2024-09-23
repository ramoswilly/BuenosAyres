package org.gamma.buenosayres.dto;

import java.util.UUID;

public class PadreDTO {
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private String telefono;
	private String email;
	private boolean habilitado;
	private boolean responsableFacturacion;

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getDni()
	{
		return dni;
	}

	public void setDni(String dni)
	{
		this.dni = dni;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getApellido()
	{
		return apellido;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public String getDireccion()
	{
		return direccion;
	}

	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
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

	public boolean isResponsableFacturacion()
	{
		return responsableFacturacion;
	}

	public void setResponsableFacturacion(boolean responsableFacturacion)
	{
		this.responsableFacturacion = responsableFacturacion;
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
