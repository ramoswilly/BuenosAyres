package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Curso;

public class MateriaRendimientoDTO {
	private Curso curso;
	private String materia; // Nombre de la materia
	private float promedio;

	public MateriaRendimientoDTO(Curso curso, String materia, float promedio) {
		this.curso = curso;
		this.materia = materia;
		this.promedio = promedio;
	}

	// Getters y Setters
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public float getPromedio() {
		return promedio;
	}

	public void setPromedio(float promedio) {
		this.promedio = promedio;
	}
}