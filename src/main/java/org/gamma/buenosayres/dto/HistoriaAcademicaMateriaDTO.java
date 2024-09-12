package org.gamma.buenosayres.dto;

import java.util.List;
import java.util.UUID;

public class HistoriaAcademicaMateriaDTO {
	private UUID idMateria;
	private String nombreMateria;
	private List<CalificacionHistoriaAcademicaDTO> calificaciones;

	public UUID getIdMateria()
	{
		return idMateria;
	}

	public void setIdMateria(UUID idMateria)
	{
		this.idMateria = idMateria;
	}

	public String getNombreMateria()
	{
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria)
	{
		this.nombreMateria = nombreMateria;
	}

	public List<CalificacionHistoriaAcademicaDTO> getCalificaciones()
	{
		return calificaciones;
	}

	public void setCalificaciones(List<CalificacionHistoriaAcademicaDTO> calificaciones)
	{
		this.calificaciones = calificaciones;
	}
}
