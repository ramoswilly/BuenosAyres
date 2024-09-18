package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Curso;

public class MejorPromedioDTO {
	private Curso curso;
	private String nombre;
	private String apellido;
	private Double promedio;

	public MejorPromedioDTO(Curso curso, String nombre, String apellido, Double promedio) {
		this.curso = curso;
		this.nombre = nombre;
		this.apellido = apellido;
		this.promedio = promedio;
	}

	// Getters
	public Curso getCurso() {
		return curso;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public Double getPromedio() {
		return promedio;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public void setPromedio(Double promedio)
	{
		this.promedio = promedio;
	}
}