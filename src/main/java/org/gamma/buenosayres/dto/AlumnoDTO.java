package org.gamma.buenosayres.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.gamma.buenosayres.model.Curso;

public class AlumnoDTO {
	private UUID id;
    private String dni;
	private String nombre;
	private String apellido;
	private String direccion;
	private Curso curso;

	public AlumnoDTO(String dni, String nombre, String apellido, String direccion, Curso curso)
	{
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.curso = curso;
	}

	public AlumnoDTO()
	{
	}
	@JsonIgnore
	public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
