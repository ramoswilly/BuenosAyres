package org.gamma.buenosayres.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.gamma.buenosayres.model.Curso;

public class AlumnoDTO {
	private UUID id;
    private String dni;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private boolean habilitado;
	private String direccion;
	private String email;
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

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public LocalDate getFechaNacimiento()
	{
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento)
	{
		this.fechaNacimiento = fechaNacimiento;
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
