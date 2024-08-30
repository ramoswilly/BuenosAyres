package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Nivel;
import org.gamma.buenosayres.model.TipoEmpleado;

import java.util.UUID;

public class ProfesorDTO {
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private String cuil;
	private String telefono;
	private String email;
	private Nivel nivel;
	private TipoEmpleado tipo;
	public ProfesorDTO()
	{
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

	public String getCuil()
	{
		return cuil;
	}

	public void setCuil(String cuil)
	{
		this.cuil = cuil;
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

	public Nivel getNivel()
	{
		return nivel;
	}

	public void setNivel(Nivel nivel)
	{
		this.nivel = nivel;
	}

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public TipoEmpleado getTipo()
	{
		return tipo;
	}

	public void setTipo(TipoEmpleado tipo)
	{
		this.tipo = tipo;
	}
}