package org.gamma.buenosayres.dto;

public class UserDataDTO {
	private String nombre;
	private String apellido;
	private String rol;

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

	public String getRol()
	{
		return rol;
	}

	public void setRol(String rol)
	{
		this.rol = rol;
	}
}
