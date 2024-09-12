package org.gamma.buenosayres.dto;

import java.util.UUID;

public class CalificacionDTO {
	private float nota;
	private UUID idAlumno;
	private UUID idEvaluacion;
	public float getNota()
	{
		return nota;
	}

	public void setNota(float nota)
	{
		this.nota = nota;
	}

	public UUID getIdAlumno()
	{
		return idAlumno;
	}

	public void setIdAlumno(UUID idAlumno)
	{
		this.idAlumno = idAlumno;
	}

	public UUID getIdEvaluacion()
	{
		return idEvaluacion;
	}

	public void setIdEvaluacion(UUID idEvaluacion)
	{
		this.idEvaluacion = idEvaluacion;
	}
}
