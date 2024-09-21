package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Curso;

public class AlumnoSancionDTO {
	private Curso curso;
	private String nombre;
	private String apellido;
	private long cantidadSanciones;

	public AlumnoSancionDTO(Curso curso, String nombre, String apellido, long cantidadSanciones) {
		this.curso = curso;
		this.nombre = nombre;
		this.apellido = apellido;
		this.cantidadSanciones = cantidadSanciones;
	}

	// Getters y setters
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

	public long getCantidadSanciones() {
		return cantidadSanciones;
	}

	public void setCantidadSanciones(long cantidadSanciones) {
		this.cantidadSanciones = cantidadSanciones;
	}
}