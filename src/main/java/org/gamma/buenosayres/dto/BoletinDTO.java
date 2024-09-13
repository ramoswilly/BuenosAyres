package org.gamma.buenosayres.dto;

import java.util.List;

public class BoletinDTO {
	private AlumnoDTO alumno;
	private List<BoletinItemDTO> calificaciones;

	public AlumnoDTO getAlumno()
	{
		return alumno;
	}

	public void setAlumno(AlumnoDTO alumno)
	{
		this.alumno = alumno;
	}

	public List<BoletinItemDTO> getCalificaciones()
	{
		return calificaciones;
	}

	public void setCalificaciones(List<BoletinItemDTO> calificaciones)
	{
		this.calificaciones = calificaciones;
	}
}
