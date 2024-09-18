package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Curso;

public class AlumnoPerfectoDTO {
	private Curso curso;
	private String nombre;
	private String apellido;

	public AlumnoPerfectoDTO(Curso curso, String nombre, String apellido) {
		this.curso = curso;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
}
