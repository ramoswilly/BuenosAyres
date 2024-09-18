package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Curso;

public class AlumnoTopDTO {
	private Curso curso;
	private String nombre;
	private String apellido;
	private int cantidadFaltas;

	public AlumnoTopDTO(Curso curso, String nombre, String apellido, int cantidadFaltas) {
		this.curso = curso;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cantidadFaltas = cantidadFaltas;
	}

	// Getters y Setters
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

	public int getCantidadFaltas() {
		return cantidadFaltas;
	}

	public void setCantidadFaltas(int cantidadFaltas) {
		this.cantidadFaltas = cantidadFaltas;
	}
}