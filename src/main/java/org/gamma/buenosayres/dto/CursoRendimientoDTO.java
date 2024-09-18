package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Curso;

public class CursoRendimientoDTO {
	private Curso curso;
	private float promedio;

	public CursoRendimientoDTO(Curso curso, float promedio) {
		this.curso = curso;
		this.promedio = promedio;
	}

	// Getters y Setters
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