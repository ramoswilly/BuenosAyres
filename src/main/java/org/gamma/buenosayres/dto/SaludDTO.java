package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Patologia;

import java.util.List;
import java.util.UUID;

public class SaludDTO {
	private UUID id;
	private String dni;
	private String nombre;
	private String apellido;
	private String obraSocial;
	private String numeroAfiliado;
	private List<Patologia> patologias;

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

	public UUID getId()
	{
		return id;
	}

	public void setId(UUID id)
	{
		this.id = id;
	}

	public String getObraSocial()
	{
		return obraSocial;
	}

	public void setObraSocial(String obraSocial)
	{
		this.obraSocial = obraSocial;
	}

	public String getNumeroAfiliado()
	{
		return numeroAfiliado;
	}

	public void setNumeroAfiliado(String numeroAfiliado)
	{
		this.numeroAfiliado = numeroAfiliado;
	}

	public List<Patologia> getPatologias()
	{
		return patologias;
	}

	public void setPatologias(List<Patologia> patologias)
	{
		this.patologias = patologias;
	}
}
