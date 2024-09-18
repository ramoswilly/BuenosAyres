package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Curso;

public class ProfesorRendimientoDTO {
	private String nombreProfesor;
	private String apellidoProfesor;
	private String materia; // Nombre de la materia
	private Curso curso;
	private float promedio;

	public ProfesorRendimientoDTO(String nombreProfesor, String apellidoProfesor, String materia, Curso curso, float promedio) {
		this.nombreProfesor = nombreProfesor;
		this.apellidoProfesor = apellidoProfesor;
		this.materia = materia;
		this.curso = curso;
		this.promedio = promedio;
	}

	// Getters y Setters
	public String getNombreProfesor() {
		return nombreProfesor;
	}

	public void setNombreProfesor(String nombreProfesor) {
		this.nombreProfesor = nombreProfesor;
	}

	public String getApellidoProfesor() {
		return apellidoProfesor;
	}

	public void setApellidoProfesor(String apellidoProfesor) {
		this.apellidoProfesor = apellidoProfesor;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public float getPromedio() {
		return promedio;
	}

	public void setPromedio(float promedio) {
		this.promedio = promedio;
	}
}