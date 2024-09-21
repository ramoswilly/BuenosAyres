package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Curso;

public class CursoProblematicoDTO {
	private Curso curso;
	private long sanciones;

	public CursoProblematicoDTO(Curso curso, long sanciones) {
		this.curso = curso;
		this.sanciones = sanciones;
	}

	// Getters y Setters
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public long getSanciones() {
		return sanciones;
	}

	public void setSanciones(long sanciones) {
		this.sanciones = sanciones;
	}
}