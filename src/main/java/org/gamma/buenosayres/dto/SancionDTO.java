package org.gamma.buenosayres.dto;

import org.gamma.buenosayres.model.Gravedad;

import java.util.Date;
import java.util.UUID;

public class SancionDTO {
	private UUID id;
	private Date fecha;
	private UUID alumnoId;
	private String nombre_alumno;
	private String apellido_alumno;
	private String causa;
	private Gravedad gravedad;
	private boolean habilitada;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public UUID getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(UUID alumnoId) {
		this.alumnoId = alumnoId;
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public Gravedad getGravedad() {
		return gravedad;
	}

	public void setGravedad(Gravedad gravedad) {
		this.gravedad = gravedad;
	}

	public boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(boolean habilitada) {
		this.habilitada = habilitada;
	}

	public String getNombre_alumno()
	{
		return nombre_alumno;
	}

	public void setNombre_alumno(String nombre_alumno)
	{
		this.nombre_alumno = nombre_alumno;
	}

	public String getApellido_alumno()
	{
		return apellido_alumno;
	}

	public void setApellido_alumno(String apellido_alumno)
	{
		this.apellido_alumno = apellido_alumno;
	}
}