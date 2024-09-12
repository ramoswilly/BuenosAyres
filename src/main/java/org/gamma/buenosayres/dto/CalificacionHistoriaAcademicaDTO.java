package org.gamma.buenosayres.dto;

import java.util.UUID;

public class CalificacionHistoriaAcademicaDTO {
	private UUID idEvaluacion;
	private String nombreEvaluacion;
	private float nota;

	public UUID getIdEvaluacion()
	{
		return idEvaluacion;
	}

	public void setIdEvaluacion(UUID idEvaluacion)
	{
		this.idEvaluacion = idEvaluacion;
	}

	public String getNombreEvaluacion()
	{
		return nombreEvaluacion;
	}

	public void setNombreEvaluacion(String nombreEvaluacion)
	{
		this.nombreEvaluacion = nombreEvaluacion;
	}

	public float getNota()
	{
		return nota;
	}

	public void setNota(float nota)
	{
		this.nota = nota;
	}
}
